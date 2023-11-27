package com.ssafy.web.plan.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.web.plan.model.PlanMentionDto;
import com.ssafy.web.plan.model.PlanVo;
import com.ssafy.web.util.MyException;

public interface PlanService {

	Map<String, String> planRegist(PlanMentionDto planMentionDto) throws MyException;

	List<PlanVo> planList() throws MyException;

	PlanVo planView(String contentid) throws MyException;

	int planDelete(String contentid, String userid) throws MyException;

	Map<String, String> planUpdate(PlanMentionDto planMentionDto) throws MyException;
}
