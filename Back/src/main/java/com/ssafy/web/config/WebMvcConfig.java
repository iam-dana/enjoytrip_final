package com.ssafy.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> getFilterRegistrationBean() {
		FilterRegistrationBean<XssEscapeServletFilter> xssRegistrationBean = new FilterRegistrationBean<>();
		xssRegistrationBean.setFilter(new XssEscapeServletFilter());
		xssRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
		xssRegistrationBean.addUrlPatterns("/*");
		return xssRegistrationBean;
	}

//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	registry.addMapping("/**")
//                .allowedOrigins("*");
//
//    }
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 등록
//				.order(1)
//				.addPathPatterns("/**")
//				.excludePathPatterns("/error", "/favicon.ico", "/user/singup", "/assets/**", "/user/login", "/", "/home", "/index.html");
//	}
//	@Bean
// 	public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//	 	filterRegistrationBean.setFilter(new LoginCheckFilter()); //내가 구현한 필터 넣기
// 	 	filterRegistrationBean.setOrder(1); //필터 체인할 때 가장 먼저 실행
// 		 filterRegistrationBean.addUrlPatterns("/*"); //모든 요청 url에 대해 실행
// 	 	return filterRegistrationBean;
// 	}

}
