package com.ssafy.web.common.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	
	public String getSalt(String userid);
	
}
