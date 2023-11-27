package com.ssafy.web.mail.service;

import java.util.UUID;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.web.util.MyException;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Override
	public String sendMail(String mail) throws MyException {
		String confirmStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

//		MimeMessage message = javaMailSender.createMimeMessage(); // 메일 내용이 html로 되어있을때 사용
//
//		try {
//			message.setFrom(new InternetAddress(senderEmail, "EnjoyTrip", "UTF-8"));
//			message.setRecipients(MimeMessage.RecipientType.TO, mail);
//			message.setSubject("EnjoyTrip 이메일 인증");
//
//			String body = "";
//			body += "<h3>" + "요청하신 인증번호 입니다." + "</h3>";
//			body += "<h1>" + "인증 번호 : " + confirmStr + "</h1>";
//			body += "<h3>" + "인증번호를 정확하게 입력해주세요." + "</h3>";
//			body += "<h3>" + "위 인증번호의 유효시간은 30분 입니다." + "</h3>";
//			body += "<h3>" + "감사합니다." + "</h3>";
//			message.setText(body, "UTF-8", "html");
//
//		} catch (Exception e) {
//			throw new MyException("메일 인증 오류");
//		}
//
//		javaMailSender.send(message);

		return confirmStr;

	}

}
