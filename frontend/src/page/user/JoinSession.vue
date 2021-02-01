<template>
  <div id="main-container" class="container">
    <div class="user" v-if="!session">
      <div class="wrapC table">
        <div class="middle">
          <div class="input-wrap">
            <input
              v-model="user.name"
              id="name"
              placeholder="사용자명을 입력해주세요"
              type="text"
            />
          </div>
          <button class="btn btn--back btn--login" @click="checkHandler()">
            입장
          </button>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
		<div id="main-video">
			<user-video :stream-manager="mainStreamManager"/>
		</div>
		<div id="video-container">
			<user-video :stream-manager="publisher"/>
			<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
		</div>
      <button id="leave-session-btn" class="btn btn--back btn--login" @click="leaveSession()">
        퇴장
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../../components/video/UserVideo';
import "../../assets/css/video.css";
import constants from "../../lib/constants";

axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	data: () => {
		return {
			constants,
			OV: undefined,
			mainStreamManager: undefined,
			session: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: undefined,
			user: {
				name: "",
			},
		};
	},
	components: {
		UserVideo,
	},
	created() {},
	watch: {},
	methods: {
		checkHandler: function() {
			let err = true;
			let msg = "";
			
			!this.user.name && ((msg = "사용자명을 입력해주세요"), (err = false));
			
			if (!err) alert(msg);
			else this.joinSession();
		},
		joinSession: function() {
			this.OV = new OpenVidu();
			
			this.session = this.OV.initSession();
			
			// stream 생성 시 session에 해당 stream을 subscriber로	
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});
			
			// stream 제거 시 session에서 해당 stream에 해당하는 subscriber 삭제
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			this.getToken(this.user.name).then(token => {
				this.session.connect(token, { clientData: this.user.name })
					.then(() => {
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined,
							videoSource: undefined,
							publishAudio: true,
							publishVideo: true,
							resolution: '640x480', // 해상도
							frameRate: 30,
							insertMode: 'APPEND',	 // target element에 추가되는 방식 (target element = 'video-container')
							mirror: false       	 // 거울모드
						});

						this.mainStreamManager = publisher;
            			this.publisher = publisher;
            
           				 // 송출
						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

      window.addEventListener('beforeunload', this.leaveSession)
      
    },
    leaveSession: function() {
      if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
    },
    
    /**
     * 쓸지도몰라서
     */
		// updateMainVideoStreamManager (stream) {
		// 	if (this.mainStreamManager === stream) return;
		// 	this.mainStreamManager = stream;
    // },
    
	getToken (mySessionId) {
		return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
    },
    
    createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`http://localhost:8080/api-sessions/create-session/`, sessionId)
					.then(response => response.data)
					.then(data => resolve(data[0]))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
    },
    
    createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`http://localhost:8080/api-sessions/generate-token/`, sessionId)
					.then(response => response.data)
					.then(data => resolve(data[0]))
					.catch(error => reject(error.response));
			});
		},
	},
};
</script>
