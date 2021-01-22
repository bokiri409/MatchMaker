package com.web.blog.controller.account;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import com.web.blog.dao.user.UserDao;
import com.web.blog.service.user.UserService;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.SignupRequest;
import com.web.blog.model.user.User;

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

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) {

        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

        ResponseEntity response = null;

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            User u = new User();
            u.setEmail(userOpt.get().getEmail());
            u.setNickname(userOpt.get().getNickname());
            result.object = (Object) u;
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        // System.out.println(request);

        if(userDao.getUserByEmail(request.getEmail()) != null){
            result.status = false;
            result.data = "중복되는 이메일 입니다!";
        }
        else if(userDao.getUserByNickname(request.getNickname()) != null){
            result.status = false;
            result.data = "중복되는 닉네임 입니다!";
        }
        else{
            User user = new User();
            // User user = new User(request.getPassword(), request.getEmail(), request.getNickname());
            user.setEmail(request.getEmail());
            user.setNickname(request.getNickname());
            user.setPassword(request.getPassword());
            user.setCertified(certified_key());
            userService.save(user);
            result.object = (Object) user;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/update")
    @ApiOperation(value = "회원정보수정")
    public Object update(@RequestBody User user){
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        System.out.println(user.getEmail());
        System.out.println(user.getNickname());
        System.out.println(user.getPassword());
        userService.updateByEmail(user.getEmail(), user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/account/delete")
    @ApiOperation(value = "회원정보탈퇴")
    public Object delete(@RequestParam(required = true) final String email){
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        userDao.deleteByEmail(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String certified_key() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }
}