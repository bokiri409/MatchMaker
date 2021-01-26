package com.web.blog.controller.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;

import com.web.blog.dao.room.RoomDao;
import com.web.blog.model.room.Room;
import com.web.blog.model.user.AuthenticationRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.Session;

@RestController
@RequestMapping("/api-sessions")
public class SessionController {
    OpenVidu openVidu;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private AuthenticationRequest user;

    private Map<Integer, Session> roomIdSession = new ConcurrentHashMap<>();
    private Map<String, Map<Integer, String>> sessionIdUserIdToken = new ConcurrentHashMap<>();

    private String OPENVIDU_URL;
    private String SECRET;

    public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @PostMapping(value = "/create-session")
    public ResponseEntity<JSONObject> createSession(@RequestBody String roomId) {
        if (!this.userIsLogged()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        int id_room = -1;
        try {
            id_room = Integer.parseInt(roomId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (this.roomIdSession.get(id_room) != null) {
            // If there's already a valid Session object for this lesson,
            // it is not necessary to ask for a new one
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            try {
                Session session = this.openVidu.createSession();

                this.roomIdSession.put(id_room, session);
                this.sessionIdUserIdToken.put(session.getSessionId(), new HashMap<>());

                showMap();

                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return getErrorResponse(e);
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