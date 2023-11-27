package com.ssafy.web.mail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.mail.service.MailService;
import com.ssafy.web.util.MyException;

@RestController
@RequestMapping("mail")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping("send")
	public Map<String, String> MailSend(@RequestBody Map<String, String> mailMap, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String mail = mailMap.get("mail");

		try {
			String confirmStr = mailService.sendMail(mail);

			HttpSession session = request.getSession();
			if (session != null) {
				session.setAttribute(mail, confirmStr);
				System.out.println(mail + " : " + confirmStr);
				map.put("msg", "인증 메일을 확인해주세요.");
			} else {
				map.put("msg", "인증 메일 전송 오류");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "인증 메일 전송 오류");
		}

		return map;
	}

	@PostMapping("confirm")
	public Map<String, Object> confirmNumber(@RequestBody Map<String, String> input, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		String userEmail = input.get("mail");
		String userInput = input.get("inputString");

		HttpSession session = request.getSession(false);
		if (session != null) {
			if (userInput.equals(session.getAttribute(userEmail))) {
				map.put("msg", "이메일 인증이 완료되었습니다.");
				map.put("isConfirmed", true);
				session.removeAttribute(userEmail);
			} else {
				map.put("msg", "이메일 인증이 실패하였습니다.");
				map.put("isConfirmed", false);
			}
		} else {
			map.put("msg", "유효 시간이 지났습니다. 다시 인증해주세요.");
			map.put("isConfirmed", false);
		}

		return map;
	}

}
