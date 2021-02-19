package com.web.blog.controller.account;

import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.dao.user.UserDao;
import com.web.blog.security.JwtAuthToken;
import com.web.blog.service.email.EmailService;
import com.web.blog.service.user.LoginService;
import com.web.blog.service.user.UserService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;

import com.web.blog.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request", response = BasicResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:8081" })
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final UserDao userDao;

    private final UserService userService;

    private final LoginService loginService;

    private final EmailService emailService;

    String success = "success";

    @PostMapping("/api/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody User user) {
        String encPassword = EncryptionUtils.encryptSHA256(user.getPassword());
        Optional<User> userOpt = userDao.findUserByEmailAndPassword(user.getEmail(), encPassword);
        final BasicResponse result = new BasicResponse();

        if (userOpt.isPresent()) {

            result.status = true;

            // JWT Auth Token 생성하고 result.data에 저장
            JwtAuthToken jwtAuthToken = (JwtAuthToken) loginService.createAuthToken(userOpt.get());
            result.data = jwtAuthToken.getToken();

            User u = new User();

            // 이메일 인증이 되지 않은 경우
            if(!userOpt.get().getCertified().equals("Y")) {
                result.data = "Not Certified Email";
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }

            u.setEmail(userOpt.get().getEmail());
            u.setNickname(userOpt.get().getNickname());
            result.object = u;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.data = "Invalid Id or Password";
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/api/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signUp(@RequestBody @Valid SignupRequest request, @ApiIgnore Errors errors) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = success;

        if (errors.hasErrors()) {   // Form Validation 에 문제가 생기면
            result.status = false;
            result.data = "형식이 유효하지 않습니다";
            // 에러 메세지를 담아서 클라이언트 측에 보냄
            result.object = userService.validateHandling(errors);

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (userDao.getUserByEmail(request.getEmail()) != null) {
            result.status = false;
            result.data = "중복되는 이메일 입니다!";

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (userDao.getUserByNickname(request.getNickname()) != null) {
            result.status = false;
            result.data = "중복되는 닉네임 입니다!";

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setNickname(request.getNickname());

            String encPassword = EncryptionUtils.encryptSHA256(request.getPassword());
            user.setPassword(encPassword);

            user.setCertified(emailService.certifiedKey());
            userService.save(user);
            result.object = user;

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PutMapping("/api/account/update")
    @ApiOperation(value = "회원정보수정")
    public Object update(@RequestBody @Valid User user, @ApiIgnore Errors errors) {
        final BasicResponse result = new BasicResponse();

        if (errors.hasErrors()) {   // Form Validation 에 문제가 생기면
            result.status = false;
            result.data = "형식이 유효하지 않습니다";
            // 에러 메세지를 담아서 클라이언트 측에 보냄
            result.object = userService.validateHandling(errors);

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (userDao.getUserByEmail(user.getEmail()) == null) {   // 회원정보에 없는 이메일이라면
            result.status = false;
            result.data = "회원정보에 등록되어 있지 않은 이메일 입니다!";

            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result.status = true;
        result.data = success;

        String encPassword = EncryptionUtils.encryptSHA256(user.getPassword());
        user.setPassword(encPassword);
        userService.updateByEmail(user.getEmail(), user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/api/account/delete")
    @ApiOperation(value = "회원정보탈퇴")
    public Object delete(@RequestParam(required = true) final String email) {
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = success;

        userDao.deleteByEmail(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}