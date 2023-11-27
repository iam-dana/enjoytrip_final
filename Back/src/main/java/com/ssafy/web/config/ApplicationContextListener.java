package com.ssafy.web.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextListener implements ServletContextListener {

	private static Map<String, HttpSession> sessions;

	public static Map<String, HttpSession> getSessions() {
		if (sessions == null) {
			sessions = new ConcurrentHashMap<String, HttpSession>();
		}
		return sessions;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		getSessions();
		ServletContextListener.super.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sessions = null;
		ServletContextListener.super.contextDestroyed(sce);
	}

}
