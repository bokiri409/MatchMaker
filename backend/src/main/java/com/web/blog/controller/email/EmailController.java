package com.web.blog.controller.email;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.web.blog.model.user.User;
import com.web.blog.service.email.EmailService;
import com.web.blog.service.user.UserService;
import com.web.blog.model.BasicResponse;

@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/email/send")
	@ApiOperation(value = "회원가입시 이메일인증")
	public Object sendMail(@RequestBody User user) throws MessagingException {
		String emailContent = "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"</head>" +
				"<body>" +
				" <div"
				+ "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
				+ "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
				+ "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Match Maker</span><br />"
				+ "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." + "	</h1>\n"
				+ "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"
				+ user.getNickname() + "		님 안녕하세요.<br />" + "		Match Maker에 가입해 주셔서 진심으로 감사드립니다.<br />"
				+ "		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />" + "		감사합니다."
				+ "	</p>" + "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""
				+ "	href=\"http://localhost:8080/email/certified?email=" + user.getEmail() + "&certified="
				+ user.getCertified() + "\" target=\"_blank\">" + "		<p"
				+ "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"
				+ "			메일 인증</p>" + "	</a>" + "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"
				+ " </div>" +
				"</body>" +
				"</html>";
		emailService.sendMail(user.getEmail(), "[Match Maker 이메일 인증]", emailContent);

		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/email/certified")
	@ApiOperation(value = "인증된 이메일 처리")
	public void checkMail(HttpServletRequest request, User user) throws MessagingException {

		User u = userService.emailCertifiedCheck(user);
		if (u != null) {
			userService.emailCertifiedUpdate(user.getEmail());
		}

	}
	
	@GetMapping(value = "email/find-password")
	@ApiOperation(value = "비밀번호 찾기 - 유저 이메일과 닉네임 일치여부 확인")
	public Object findPassword(String email, String nickname) {
		final BasicResponse result = new BasicResponse();

		result.status = userService.checkEmailAndNickname(email, nickname);

		if(result.status) {
			result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.data = "fail";
			return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(value = "email/find-password/send-mail")
	@ApiOperation(value = "유저의 비밀번호를 임시 비밀번호로 변경, 등록된 이메일로 임시 비밀번호 전송")
	public Object sendTempPasswordMail(@RequestBody User user) throws MessagingException {
		String tempPassword = userService.getTempPassword();
		userService.updatePassword(tempPassword, user.getEmail());

		String emailContent = "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"</head>" +
				"<body>" +
				" <div"
				+ "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
				+ "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
				+ "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Match Maker</span><br />"
				+ "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." + "	</h1>\n"
				+ "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"
				+ user.getNickname() + "		님 안녕하세요.<br />" + "		Match Maker의 임시 비밀번호를 안내드립니다.<br />"
				+ "	 임시 비밀번호 : " + tempPassword
				+ "<br />		감사합니다."
				+ "	</p>" +
				"	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"
				+ " </div>" +
				"</body>" +
				"</html>";

		emailService.sendMail(user.getEmail(), "[Match Maker 임시 비밀번호 발송]", emailContent);

		final BasicResponse result = new BasicResponse();
		result.status = true;
		result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}