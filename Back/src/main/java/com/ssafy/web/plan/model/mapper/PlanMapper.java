package com.ssafy.web.plan.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.web.plan.model.PlanVo;
import com.ssafy.web.util.MyException;

@Mapper
public interface PlanMapper {

	void planRegist(PlanVo plan) throws MyException;

	List<PlanVo> planList() throws MyException;

	PlanVo planView(String contentid) throws MyException;

	int delete(String contentid, String userid) throws MyException;

	void planUpdate(PlanVo plan) throws MyException;

}
