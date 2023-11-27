package com.ssafy.web.common.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.common.model.mapper.CommonMapper;

@Service
public class CommonServiceImpl implements CommonService{

	@Autowired
	CommonMapper commonMapper;
	
	
	@Override
	public String getSalt(String userid) {
		return commonMapper.getSalt(userid);
	}

}
