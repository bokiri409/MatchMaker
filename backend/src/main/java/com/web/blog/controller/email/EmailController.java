package com.web.blog.controller.email;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	@PostMapping(value = "/email/send")
	public Object sendmail(@RequestBody User user) throws MessagingException {
		StringBuffer emailcontent = new StringBuffer();
		emailcontent.append("<!DOCTYPE html>");
		emailcontent.append("<html>");
		emailcontent.append("<head>");
		emailcontent.append("</head>");
		emailcontent.append("<body>");
		emailcontent.append(
				" <div" 																																																	+ 
				"	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+ 
				"	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+ 
				"		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Match Maker</span><br />"																													+ 
				"		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+ 
				"	</h1>\n"																																																+ 
				"	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+ 
				user.getNickname()																																																+
				"		님 안녕하세요.<br />"																																													+ 
				"		Match Maker에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+ 
				"		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"																													+ 
				"		감사합니다."																																															+ 
				"	</p>"																																																	+ 
				"	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""																																	+
				"	href=\"http://localhost:8080/email/certified?email=" + user.getEmail() + "&certified=" + user.getCertified() + "\" target=\"_blank\">"														+ 
				"		<p"																																																	+
				"			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"							+ 
				"			메일 인증</p>"																																														+ 
				"	</a>"																																																	+
				"	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
				" </div>"
		);
		emailcontent.append("</body>");
		emailcontent.append("</html>");
		emailService.sendMail(user.getEmail(), "[Match Maker 이메일 인증]", emailcontent.toString());

		final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

		return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @Autowired
	private UserService userService;

	@GetMapping(value = "/email/certified")
	public void checkmail(HttpServletRequest request, User user) throws MessagingException {

		User u = userService.email_certified_check(user);
		System.out.println(u);
		if(u != null) {
			userService.email_certified_update(user.getEmail(), user);
			// SecurityContextHolder.getContext().setAuthentication(null);
		}

	}
    
}