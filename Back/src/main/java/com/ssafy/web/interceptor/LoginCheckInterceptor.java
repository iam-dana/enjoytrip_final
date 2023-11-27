package com.ssafy.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	private static final String SESSION_ID = "sessionId";
	
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String requestURI = request.getRequestURI();
//		System.out.println("[interceptor] : " + requestURI);
//		HttpSession session = request.getSession(false);
//		
//		if(session == null || session.getAttribute("userid") == null) {
//       		// 로그인 되지 않음
//			System.out.println("[미인증 사용자 요청]");
//			
//			//로그인으로 redirect
////			response.sendRedirect("/login?redirectURL=" + requestURI);
//			
//			response.sendRedirect("/");
//			return false;
//		}
//        // 로그인 되어있을 때
//		return true;
//	
//    }
}
