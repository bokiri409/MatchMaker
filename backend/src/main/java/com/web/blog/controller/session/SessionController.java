package com.web.blog.controller.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;

import com.web.blog.dao.room.RoomDao;
import com.web.blog.model.room.Room;
import com.web.blog.model.user.AuthenticationRequest;

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


@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/api-sessions")
public class SessionController {
    OpenVidu openVidu;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private AuthenticationRequest user;

    private Map<String, Session> roomIdSession = new ConcurrentHashMap<>();
    private Map<String, Map<String, String>> sessionIdUserIdToken = new ConcurrentHashMap<>();

    private String OPENVIDU_URL;
    private String SECRET;

    public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping(value = "/create-session")
    public ResponseEntity<JSONObject> createSession(@RequestBody String roomId) {
        // if (!this.userIsLogged()) {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }

        //int id_room = Integer.parseInt(roomId);
       
        JSONObject responseJson = new JSONObject();
        
        if (this.roomIdSession.get(roomId) != null) {
            // If there's already a valid Session object for this lesson,
            // it is not necessary to ask for a new one
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            try {
                Session session = this.openVidu.createSession();
                
                System.out.println("세션 생성");
                this.roomIdSession.put(roomId, session);
                this.sessionIdUserIdToken.put(session.getSessionId(), new HashMap<>());

                showMap();

                responseJson.put(0, roomId);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            } catch (Exception e) {
                return getErrorResponse(e);
            }
        }
    }

    @PostMapping(value = "/generate-token")
	public ResponseEntity<JSONObject> generateToken(@RequestBody String roomId) {

		// if (!this.userIsLogged()) {
		// 	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }

        //int id_room = Integer.parseInt(roomId);

		// if (this.roomIdSession.get(id_room) == null) {
		// 	System.out.println("There's no Session fot this room");
		// 	return new ResponseEntity<>(HttpStatus.CONFLICT);
		// }

		Session session = this.roomIdSession.get(roomId);
		OpenViduRole role = OpenViduRole.PUBLISHER;

		JSONObject responseJson = new JSONObject();
		TokenOptions tokenOpts = new TokenOptions.Builder().role(role)
				.data("SERVER=" + "hello").build();
		try {
			String token = this.roomIdSession.get(roomId).generateToken(tokenOpts);

			//this.sessionIdUserIdToken.get(session.getSessionId()).put(0, token);
			responseJson.put(0, token);
			showMap();

			return new ResponseEntity<>(responseJson, HttpStatus.OK);
		} catch (OpenViduJavaClientException e1) {
			return getErrorResponse(e1);
		} catch (OpenViduHttpException e2) {
			if (404 == e2.getStatus()) {
				try {
					this.sessionIdUserIdToken.remove(session.getSessionId());
					session = this.openVidu.createSession();
					this.roomIdSession.put(roomId, session);
					this.sessionIdUserIdToken.put(session.getSessionId(), new HashMap<>());
					String token = session.generateToken(tokenOpts);

					//this.sessionIdUserIdToken.get(session.getSessionId()).put(1, token);
					responseJson.put(0, token);
					showMap();

					return new ResponseEntity<>(responseJson, HttpStatus.OK);
				} catch (OpenViduJavaClientException | OpenViduHttpException e3) {
					return getErrorResponse(e3);
				}
			} else {
				return getErrorResponse(e2);
			}
		}
    }
    
    private boolean userIsLogged() {
        if (!user.isLoggedUser()) {
            System.out.println("Not user logged");
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