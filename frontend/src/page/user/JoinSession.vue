<template>
  <div id="main-container">
    <div class="user" v-if="!session">
		<v-container fill-height style="max-width: 400px;" fluid>
        	<v-layout align-center row wrap>
				<v-flex xs10>
					<h1>😘💻</h1>
					<h4><strong>상대방도 기다리고 있습니다!</strong></h4>
					<base-text>미팅 방을 생성하거나 입장하세요.</base-text>
					<v-text-field
                        v-model="roomId"
						label="방 제목을 입력해주세요."
						id="name"
						placeholder="방 제목을 입력해주세요."
						filled
						solo
						rounded
						dense
					></v-text-field>
					<v-btn
						rounded
						block
						color="#F2ACC6"
                        @click="checkHandler()"
					>
						입장
					</v-btn>
				</v-flex>
			</v-layout>
		</v-container>
    </div>

    <div id="session" v-if="session">
		<div id="background-bottom"></div>
		<div id="main-video-waiting" v-if="!mainStreamManager">
			<div>
				<p>상대방의 연결을 기다리는 중입니다.</p>
			</div>
		</div>
		<div id="main-video" v-if="mainStreamManager">
			<user-video :stream-manager="mainStreamManager"/>
		</div>
		<div id="video-container">
			<muted-user-video :stream-manager="subStreamManager"/>
		</div>
		
		<modal name="virtual-background-modal">
			<p class="modal-title">가상 배경 선택</p>
			<section style="text-align:center;">
				<p class="modal-description">적용할 가상 배경 이미지의 url을 입력해주세요.</p>
				<v-text-field
                    v-model="virtualBackgroundURL"
					label="가상 배경 이미지 URL"
					id="virtualBackgroundURL"
					filled
					solo
					rounded
				></v-text-field>
				<v-btn id="virtual-background-activate-btn" @click="setVirutalBackground()">적용하기</v-btn>
				<v-btn id="virtual-background-delete-btn" @click="removeFilter()">해제하기</v-btn>
			</section>
			<button class="modal-hide" @click="hideVirtualBackgroundModal()">OK</button>
		</modal>

		<modal name="background-music-modal">
			<p class="modal-title">배경 음악 선택</p>
			<v-carousel v-model="backgroundMusicIndex" class="background-music-carousel" height="230" hide-delimiter-background show-arrows-on-hover>
				<v-carousel-item class="background-music-carousel-item" v-for="(musicTitle, i) in musicTitles" :key="i">
					<v-sheet :color="colors[i]"	height="100%">
					<v-row class="fill-height" align="center" justify="center">
						<div class="display-1">
							{{ musicTitle }}
						</div>
					</v-row>
					</v-sheet>
				</v-carousel-item>
			</v-carousel>
			<button class="modal-hide" @click="hideBackgroundMusicModal()">OK</button>
		</modal>

		<modal name="filter-modal">
			<p class="modal-title">적용할 필터 선택</p>
			<section>
				<template v-for="fo in filterOptions">
					<input type="radio" class="input-radio" v-model="filter" :id="fo.id" :value="fo.value" :key="fo.id">{{fo.id}}
					<br :key="fo.value"/>
				</template>
			</section>
			<button class="modal-hide" @click="hideFilterModal()">OK</button>
		</modal>
		
		<v-fab-transition>
			<v-btn v-show="!isMenuHidden" id="virtual-background-btn" class="btn btn--back btn--login" @click="showVirtualBackgroundModal()">
				가상 배경
			</v-btn>
		</v-fab-transition>
		
		<v-fab-transition>
			<v-btn v-show="!isMenuHidden" id="background-music-btn" class="btn btn--back btn--login" @click="showBackgroundMusicModal()">
				배경 음악
			</v-btn>
		</v-fab-transition>

		<v-fab-transition>
			<v-btn v-show="!isMenuHidden" id="filter-modal-btn" class="btn btn--back btn--login" @click="showFilterModal()">
				필터
			</v-btn>
		</v-fab-transition>

		<v-fab-transition>
			<v-btn v-show="!isMenuHidden" id="leave-session-btn" class="btn btn--back btn--login" @click="leaveSession()">
				퇴장
			</v-btn>
		</v-fab-transition>

		<v-btn id="show-menu" @click="isMenuHidden=!isMenuHidden">
              {{ isMenuHidden ? '메뉴' : '숨기기' }}
        </v-btn>

		<audio id="background-music" :loop="true" :src="backgroundMusic" style="display:none;" preload autoplay></audio>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../../components/video/UserVideo';
import MutedUserVideo from '../../components/video/MutedUserVideo';
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
			session: undefined,
			publisher: undefined,
			mainStreamManager: undefined,
			subStreamManager: undefined,
			mainStream: undefined,
			subStream: undefined,
			mySessionId: undefined,
			filter: undefined,
			isFilter: false,
			filterOptions: [{id: '흑백화면', value: 'Grayscale'}, {id: '상하반전', value: 'Rotation'}, {id: '테두리', value: 'Videobox'},
							{id: '시간 기록', value: 'Time'}, {id: '시계', value: 'Clock'}, {id: '머리 위 강아지', value: 'Faceoverlay'}, {id: '필터 해제하기', value: 'NoFilter'}, ],
			roomId: "",
			isMenuHidden: true,
			virtualBackgroundURL: undefined,
			backgroundMusicIndex: 0,
			backgroundMusic: undefined,
			colors: [
				'indigo',
				'warning',
				'pink darken-2',
				'red lighten-1',
			],
			musicTitles: [
				'노래 끄기',
				'Peacefully',
				'Cocktail Hour',
				'Warm Afternoon Outdoors',
			],
			musicPaths: [
				undefined,
				require('@/assets/music/Peacefully - E\'s Jammy Jams.mp3'),
				require('@/assets/music/Cocktail Hour - Aaron Kenny.mp3'),
				require('@/assets/music/Warm Afternoon Outdoors.mp3'),
			],
		};
	},
	components: {
		UserVideo,
		MutedUserVideo,
	},
	created() {},
	watch: {},
	methods: {
		checkHandler: function() {
			// 로컬스토리지에 유저 데이터가 없으면 미팅방 생성 불가
			if (!localStorage.getItem('userData')){
				alert("로그인 후에 미팅방 생성이 가능합니다.");
				this.$router.push({ // 로그인 페이지로 이동
					path: "/user/login",
				});
			}
			else{ // 로그인 후
				let err = true;
				let msg = "";

				!this.roomId && ((msg = "방 제목을 입력해주세요"), (err = false));

				if (!err) alert(msg);
				else this.joinSession();
			}
		},
	
		joinSession: function() {
			this.OV = new OpenVidu();
			
			this.session = this.OV.initSession();
			
			// stream 생성 시 session에 해당 stream을 subscriber로	
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subStream = subscriber;
				this.mainStreamManager = this.subStream;
				this.subStreamManager = this.mainStream;
			});
			
			// stream 제거 시 session에서 해당 stream에 해당하는 subscriber 삭제
			this.session.on('streamDestroyed', ({}) => {
				this.mainStreamManager = undefined;
			});

			this.getToken(this.roomId).then(token => {
				this.session.connect(token, { clientData: this.roomId })
					.then(() => {
						
						let publisher = this.OV.initPublisher(this.mainStreamManager, {
							audioSource: undefined,
							videoSource: undefined,
							publishAudio: true,
							publishVideo: true,
							resolution: '640x480', // 해상도
							frameRate: 30,
							insertMode: 'APPEND',	 // target element에 추가되는 방식 (target element = 'video-container')
							mirror: true,       	 // 거울모드
						});

						this.mainStream = publisher;
						this.subStreamManager = this.mainStream;

						// 송출
						this.mainStream.subscribeToRemote();
						this.session.publish(this.mainStream);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

      		window.addEventListener('beforeunload', this.leaveSession)
      
    	},
		
		swapMainVideoStreamManager () {
		},
		
		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},
    
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(this.$api_url + `/api-sessions/create-session/`, sessionId)
					.then(response => response.data)
					.then(data => resolve(data[0]))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} 
						else if (error.response.data.message.slice(0,35) == "io.jsonwebtoken.ExpiredJwtException") {
							alert("로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
							this.$store.dispatch("LOGOUT", this.user).then(() =>
								this.$router.push({
								path: "/account/login",
								})
							)
							.catch(() => {});
						}
						else {
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
					.post(this.$api_url + `/api-sessions/generate-token/`, sessionId)
					.then(response => response.data)
					.then(data => resolve(data[0]))
					.catch(error => reject(error.response));
			});
		},

		leaveSession: function() {
			axios
				.post(this.$api_url + `/api-sessions/remove-user/`, this.roomId)
				.then(response => {
					if(response.status == 200){
						
						this.mainStream = undefined;
						this.subStream = undefined;
						this.session = undefined;
						this.mainStreamManager = undefined;
						this.publisher = undefined;
						this.subStreamManager = undefined;
						this.OV = undefined;

						window.removeEventListener('beforeunload', this.leaveSession);
					}
				})
				.catch(error => console.warn(error.response));
		},

		showVirtualBackgroundModal () {
            this.$modal.show('virtual-background-modal');
		},
		
        hideVirtualBackgroundModal () {
			this.$modal.hide('virtual-background-modal');
		},

		showBackgroundMusicModal () {
            this.$modal.show('background-music-modal');
		},
		
        hideBackgroundMusicModal () {
			this.$modal.hide('background-music-modal');
			this.backgroundMusic = this.musicPaths[this.backgroundMusicIndex];
			this.$el.querySelectorAll('audio')[0].volume = 0.4;
			if(this.backgroundMusic == undefined)
				this.$el.querySelectorAll('audio')[0].pause();
		},

		// filter 관련 함수
		
		showFilterModal () {
            this.$modal.show('filter-modal');
		},
		
        hideFilterModal () {
			this.$modal.hide('filter-modal');
			this.applyFilter();
		},
		
		applyFilter() {
			var filterOption = { type: '', options: {} };
			var type = this.filter;
			
			if(this.mainStream === undefined){
				alert("영상이 없습니다...")
			}
			else{
				this.removeFilter();
				switch (type) {
					case 'Grayscale':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": "videobalance saturation=0.0" };
						break;
					case 'Rotation':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": "videoflip method=vertical-flip" };
						break;
					case 'Faceoverlay':
						filterOption.type = 'FaceOverlayFilter';
						filterOption.options = {};
						break;
					case 'Videobox':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": "videobox fill=black top=-30 bottom=-30 left=-30 right=-30" };
						break;
					case 'Text':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": 'textoverlay text="Embedded text!" valignment=top halignment=right font-desc="Cantarell 25" draw-shadow=false' };
						break;
					case 'Time':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": 'timeoverlay valignment=bottom halignment=right font-desc="Sans, 20"' };
						break;
					case 'Clock':
						filterOption.type = 'GStreamerFilter';
						filterOption.options = { "command": 'clockoverlay valignment=bottom halignment=right shaded-background=true font-desc="Sans, 20"' };
						break;
				}
				if(type != 'NoFilter'){
					this.isFilter = true;

					this.mainStream.stream.applyFilter(filterOption.type, filterOption.options)
						.then(f => {
							if (f.type === 'FaceOverlayFilter') {
								f.execMethod(
									"setOverlayedImage",
									{
										"uri": "http://clipart-library.com/images_k/dogs-transparent-background/dogs-transparent-background-15.png",
										"offsetXPercent": "-0.1F",
										"offsetYPercent": "-1.2F",
										"widthPercent": "1.0F",
										"heightPercent": "1.5F"
									});
							}
						});
				}
			}
		},

		setVirutalBackground(){
			if(this.mainStream === undefined){
				alert("영상이 없습니다...")
			}
			else{
				var filterOption = { 
					type: 'ChromaFilter', 
					options: {
						window: {
							topRightCornerX: 0,
							topRightCornerY: 0,
							width: 50,
							height: 50
						},
						backgroundImage: this.virtualBackgroundURL
					}
				};

				this.removeFilter();

				this.isFilter = true;
				this.mainStream.stream.applyFilter(filterOption.type, filterOption.options);
			}
		},

		removeFilter() {
			if(this.isFilter)
				this.mainStream.stream.removeFilter();
			this.isFilter = false;
		}
	},

};
</script>
