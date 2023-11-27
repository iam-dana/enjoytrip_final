package com.ssafy.web.loginhistory.service;

import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.user.model.UserDto;
import com.ssafy.web.util.MyException;

public interface LoginHistoryService {
	public int failLogin(LoginHistoryDto lhd) throws MyException;

	public void successLogin(UserDto userDto, LoginHistoryDto lhd) throws MyException;

	public void deleteHistory(String userid, String userip);

	public void delete(String userid);

	
}
