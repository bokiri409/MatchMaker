package com.web.blog.controller.account;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import com.web.blog.dao.user.UserDao;
import com.web.blog.security.JwtAuthToken;
import com.web.blog.service.user.LoginService;
import com.web.blog.service.user.UserService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;

import com.web.blog.utils.EncryptionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;

    String success = "success";

    @PostMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody User user) {
        String encPassword = EncryptionUtils.encryptSHA256(user.getPassword());
        Optional<User> userOpt = userDao.findUserByEmailAndPassword(user.getEmail(), encPassword);

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;

            // JWT Auth Token 생성하고 result.data에 저장
            JwtAuthToken jwtAuthToken = (JwtAuthToken) loginService.createAuthToken(userOpt.get());
            result.data = jwtAuthToken.getToken();

            User u = new User();
            u.setEmail(userOpt.get().getEmail());
            u.setNickname(userOpt.get().getNickname());
            result.object = u;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = success;

        if (userDao.getUserByEmail(request.getEmail()) != null) {
            result.status = false;
            result.data = "중복되는 이메일 입니다!";
        } else if (userDao.getUserByNickname(request.getNickname()) != null) {
            result.status = false;
            result.data = "중복되는 닉네임 입니다!";
        } else {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setNickname(request.getNickname());

            String encPassword = EncryptionUtils.encryptSHA256(request.getPassword());
            user.setPassword(encPassword);

            user.setCertified(certifiedKey());
            userService.save(user);
            result.object = user;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/update")
    @ApiOperation(value = "회원정보수정")
    public Object update(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = success;

        String encPassword = EncryptionUtils.encryptSHA256(user.getPassword());
        user.setPassword(encPassword);
        userService.updateByEmail(user.getEmail(), user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/account/delete")
    @ApiOperation(value = "회원정보탈퇴")
    public Object delete(@RequestParam(required = true) final String email) {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = success;

        userDao.deleteByEmail(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String certifiedKey() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            }
        } while (sb.length() < 10);
        return sb.toString();
    }
}