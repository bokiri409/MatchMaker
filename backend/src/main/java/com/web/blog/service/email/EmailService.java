package com.web.blog.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
	
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) throws MessagingException {
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	
    	helper.setFrom("MatchMaker"); //보내는사람
    	helper.setTo(toEmail); //받는사람
    	helper.setSubject(subject); //메일제목
    	helper.setText(message, true); //ture넣을경우 html

        javaMailSender.send(mimeMessage);
    }

    public String certifiedKey() {
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