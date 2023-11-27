package com.ssafy.web.user.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.web.user.model.UserDto;


@Mapper
@Repository
public interface UserMapper {
	public void signup(UserDto userDto) throws Exception;

	public String getUserNameById(String userid) throws Exception;

	public UserDto login(UserDto userDto) throws Exception;

	public String getUserPassword(String userid) throws Exception;

	public void changePassword(UserDto userDto);

	public void delete(String userid);

	public void saveToken(UserDto m);

	public int idCheck(String userid);

	public void changeuserinfo(UserDto u);

	public UserDto checkEmail(UserDto u);
	
	public List<String> userList() throws Exception;

	public String loginByKakao(String email);

}
