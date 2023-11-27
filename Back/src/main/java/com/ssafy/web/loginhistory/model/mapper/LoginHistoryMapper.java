package com.ssafy.web.loginhistory.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.util.MyException;

@Mapper
@Repository
public interface LoginHistoryMapper {
	LoginHistoryDto checkHistory(String userid, String userip) throws MyException;

	void updateHistoryFail(LoginHistoryDto history) throws MyException;

	void updateHistorySuccess(LoginHistoryDto history) throws MyException;

	void firstHistory(LoginHistoryDto history) throws MyException;

	void delete(String userid, String userip);

	void delete(String userid);

	void deleteHistory(String userid, String userip);
}
