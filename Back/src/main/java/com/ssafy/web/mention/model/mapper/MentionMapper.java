package com.ssafy.web.mention.model.mapper;

import java.util.List; 

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.web.mention.model.MentionVo;
import com.ssafy.web.util.MyException;

@Mapper
public interface MentionMapper {

	void mentionRegist(MentionVo mention) throws MyException;

	List<MentionVo> mentionList(String userid) throws MyException;

	void mentionUpdate(MentionVo mention) throws MyException;
	
	List<String> mentionView(String contentid) throws MyException;
	
	void mentionDelete(String contentid) throws MyException;

}
