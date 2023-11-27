package com.ssafy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ssafy.web.common.model.mapper.CommonMapper;
import com.ssafy.web.config.MyTripDatabaseConfig;
import com.ssafy.web.config.TripSecDatabaseConfig;
import com.ssafy.web.loginhistory.model.mapper.LoginHistoryMapper;
import com.ssafy.web.mention.model.mapper.MentionMapper;
import com.ssafy.web.plan.model.mapper.PlanMapper;
import com.ssafy.web.sec.model.mapper.SecMapper;
import com.ssafy.web.user.model.mapper.UserMapper;

@SpringBootApplication(scanBasePackages = {"com.ssafy.web.*"})
public class EnjoyTripApplication {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SecMapper secMapper;
	
	@Autowired
	private PlanMapper planMapper;
	
	@Autowired
	private LoginHistoryMapper loginHistoryMapper;
	
	@Autowired
	private MentionMapper mentionMapper;
	
	@Autowired
	private CommonMapper commonMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(EnjoyTripApplication.class, args);
	}

}
