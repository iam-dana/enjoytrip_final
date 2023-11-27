package com.ssafy.web.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.user.model.UserDto;

public interface UserService {
	public boolean signUp(UserDto userDto) throws Exception;

	public String getUserNameById(String userid) throws Exception;

	public Map login(UserDto userDto, LoginHistoryDto lhd) throws Exception;

	public String getUserPassword(String userid) throws Exception;

	public Map changePassword(UserDto userDto, String newPw) throws Exception;

	public String deleteUser(UserDto u) throws Exception;

	public int idCheck(String userid);

	public void changeuserinfo(UserDto u);

	public UserDto checkUser(UserDto u) throws Exception;

	public UserDto checkEmail(UserDto u);
	
	public List<String> userList() throws Exception;
	
	public String loginByKakao(String email);
	
}
