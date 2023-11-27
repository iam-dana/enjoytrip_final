package com.ssafy.web.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;

@WebListener
@Configuration
public class SessionConfig implements HttpSessionListener {
	
	private static final Map<String, HttpSession> sessions = ApplicationContextListener.getSessions();
	// 생성된 세션들을 전부 map에 저장 key는 jsessionid, value는 session

	public synchronized static String sessionIdCheck(String type, String inputId) {
		// 비교를 원하는 type과 id를 받아서
		// 지금까지 로그인 된 세션들 중 이미 로그인 된 세션이 있는 지 확인하고 있으면 원래 세션 무효화
		String result = "";

		System.out.println("sessions 객체 주소 : " + sessions.hashCode());

		for (String key : sessions.keySet()) { // 세션들 다 돌면서
			HttpSession value = sessions.get(key);

			if (value != null && value.getAttribute(type) != null) { // 세션이 있고 id attr에 값이 있고
				if (value.getAttribute(type).toString().equals(inputId)) { // 그 값과 inputId가 같으면
					result = key.toString(); // 원래 로그인 되어있던 세션의 아이디를 저장
				}
			}
		}
		removeMultiLoginId(result); // 원래 로그인 된 세션을 삭제
		return result; // 삭제된 jsessionid
	}

	private static void removeMultiLoginId(String existingJId) {
		// 입력받은 jsessionid에 해당하는 세션을 삭제하는 함수
		System.out.println("removed id : " + existingJId);
		if (existingJId != null && existingJId.length() > 0) { // id가 있고 값이 있으면
			sessions.get(existingJId).invalidate(); // 해당 아이디에 해당하는 세션을 무효화
			sessions.remove(existingJId); // map에서도 삭제
		}
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("sessionCreated : " + se.getSession().getId());
		sessions.put(se.getSession().getId(), se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("sessionDestroyed : " + se.getSession().getId());
		String removed = se.getSession().getId();
		if (sessions.get(removed) != null) {
			sessions.get(removed).invalidate();
			sessions.remove(removed);
		}
	}

}
