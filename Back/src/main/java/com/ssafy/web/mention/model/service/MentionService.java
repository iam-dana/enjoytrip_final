package com.ssafy.web.mention.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.web.mention.model.MentionVo;
import com.ssafy.web.plan.model.PlanMentionDto;
import com.ssafy.web.util.MyException;

public interface MentionService {

	Map<String, String> mentionRegist(PlanMentionDto planMentionDto) throws MyException;

	List<MentionVo> mentionList(String userid) throws MyException;
	
	void mentionUpdate(MentionVo mention) throws MyException;
	
	List<String> mentionView(String contentid) throws MyException;
	
	void mentionDelete(String contentid) throws MyException;

}
