package com.ssafy.web.sec.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.web.sec.model.SecDto;

@Mapper
@Repository
public interface SecMapper {

	public void insertSecurity(SecDto secDto) throws Exception;

	public String readSalt(String userid) throws Exception;

	public void updateSalt(SecDto secDto);

	public void delete(String userid);

	public String readIv(String userid);

}
