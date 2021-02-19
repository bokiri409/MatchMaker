package com.web.blog.controller.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.web.blog.dao.room.RoomDao;
import com.web.blog.model.user.AuthenticationRequest;

import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import io.openvidu.java.client.KurentoOptions;


@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/api/api-sessions")
public class SessionController {
    OpenVidu openVidu;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private AuthenticationRequest user;

    private Map<String, Session> roomIdSession = new ConcurrentHashMap<>();
    private Map<String, Map<Integer, String>> sessionIdUserIdToken = new ConcurrentHashMap<>();

    private String OPENVIDU_URL;
    private String SECRET;

    public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping(value = "/create-session")
    @ApiOperation(value = "세션 생성")
    public ResponseEntity<JSONObject> createSession(@RequestBody String roomId) {
       
        JSONObject responseJson = new JSONObject();
        
        if (this.roomIdSession.get(roomId) != null) {
            // 이미 이 방에 유효한 세션이 있다면
            // 새로운 세션을 만들 필요가 없음
            responseJson.put(0, roomId);
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } else {
            try {
                Session session = this.openVidu.createSession();

                this.roomIdSession.put(roomId, session);
                this.sessionIdUserIdToken.put(session.getSessionId(), new HashMap<>());

                responseJson.put(0, roomId);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            } catch (Exception e) {
                return getErrorResponse(e);
            }
        }
    }

    @PostMapping(value = "/generate-token")
    @ApiOperation(value = "토큰 생성")
	public ResponseEntity<JSONObject> generateToken(@RequestBody String roomId) {

		Session session = this.roomIdSession.get(roomId);
		OpenViduRole role = OpenViduRole.PUBLISHER;
        
        JSONObject responseJson = new JSONObject();

        TokenOptions tokenOptions = new TokenOptions.Builder()
            .role(role)
            .data("user_data")
            .kurentoOptions(
                new KurentoOptions.Builder().allowedFilters(
                new String[]{"GStreamerFilter", "FaceOverlayFilter", "ChromaFilter"}).build())
            .build();
            
		try {
            String token = session.generateToken(tokenOptions);

			responseJson.put(0, token);

			return new ResponseEntity<>(responseJson, HttpStatus.OK);
		} catch (OpenViduJavaClientException e1) {
			return getErrorResponse(e1);
		} catch (OpenViduHttpException e2) {
			if (404 == e2.getStatus()) {
			    // 유효하지 않은 세션ID (예측하지 못한 유저의 방 나가기)
                // 세션 오브젝트가 더 이상 유효하지 않음.
                // 유효하지 않은 세션을 삭제하고 새로운 세션을 만들어 줌.
				try {
					this.sessionIdUserIdToken.remove(session.getSessionId());
					session = this.openVidu.createSession();
					this.roomIdSession.put(roomId, session);
					this.sessionIdUserIdToken.put(session.getSessionId(), new HashMap<>());
					String token = session.generateToken(tokenOptions);

					responseJson.put(0, token);

					return new ResponseEntity<>(responseJson, HttpStatus.OK);
				} catch (OpenViduJavaClientException | OpenViduHttpException e3) {
					return getErrorResponse(e3);
				}
			} else {
				return getErrorResponse(e2);
			}
		}
    }

    @PostMapping(value = "/remove-user")
    @ApiOperation(value = "유저가 방에서 나갈 때 삭제")
    public ResponseEntity<JSONObject> removeUser(@RequestBody String roomId) throws Exception {

        if (this.roomIdSession.get(roomId) == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String sessionId = this.roomIdSession.get(roomId).getSessionId();
            if (this.sessionIdUserIdToken.get(sessionId).isEmpty()) {
                // 나간 유저가 방에 남은 마지막 유저였다면
                this.roomIdSession.remove(roomId);
                this.sessionIdUserIdToken.remove(sessionId);
            }

            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private boolean userIsLogged() {
        if (!user.isLoggedUser()) {
            System.out.println("유저가 로그인 되어있지 않음");
            return false;
        }
        return true;
    }

    private void showMap() {
        System.out.println("------------------------------");
        System.out.println(this.roomIdSession.toString());
        System.out.println(this.sessionIdUserIdToken.toString());
        System.out.println("------------------------------");
    }

    private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
        JSONObject json = new JSONObject();
        json.put("cause", e.getCause());
        json.put("error", e.getMessage());
        json.put("exception", e.getClass());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}