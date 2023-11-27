//package com.ssafy.web.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	  @Override
//	  protected void configure(HttpSecurity http) throws Exception {
//	    /* 중간 생략 */
//	    
//	    http
//	        .sessionManagement()
//	        .sessionFixation().changeSessionId()
//	        .maximumSessions(1)
//	        .maxSessionsPreventsLogin(true)
//	    ;
//	  }
//
//}
