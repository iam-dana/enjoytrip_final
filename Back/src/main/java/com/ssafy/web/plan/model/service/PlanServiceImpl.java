package com.ssafy.web.plan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.plan.model.PlanMentionDto;
import com.ssafy.web.plan.model.PlanVo;
import com.ssafy.web.plan.model.mapper.PlanMapper;
import com.ssafy.web.util.MyException;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanMapper planMapper;

	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}

	@Override
	public Map<String, String> planRegist(PlanMentionDto planMentionDto) throws MyException {
		Map<String, String> map = new HashMap<String, String>();

		String contentid = planMentionDto.getContentid();
		String title = planMentionDto.getTitle();
		String userid = planMentionDto.getUserid();
		String content = planMentionDto.getContent();

		if (title == null || userid == null || content == null || "".equals(title) || "".equals(userid)
				|| "".equals(content)) { // null 체크
			map.put("msg", "제목, 내용은 비어 있을 수 없습니다.");
			return map;
		}
		try {
			PlanVo plan = new PlanVo();

			plan.setContentid(contentid);
			plan.setTitle(title);
			plan.setUserid(userid);
			plan.setContent(content);

			planMapper.planRegist(plan);

		} catch (Exception e) {
			map.put("msg", "여행 계획 오류");
		}

		return map;

	}

	@Override
	public List<PlanVo> planList() throws MyException {
		return planMapper.planList();
	}

	@Override
	public PlanVo planView(String contentid) throws MyException {
		return planMapper.planView(contentid);
	}

	@Override
	public int planDelete(String contentid, String userid) throws MyException {
		return planMapper.delete(contentid, userid);
	}

	@Override
	public Map<String, String> planUpdate(PlanMentionDto planMentionDto) {
		Map<String, String> map = new HashMap<String, String>();

		String contentid = planMentionDto.getContentid();
		String title = planMentionDto.getTitle();
		String userid = planMentionDto.getUserid();
		String content = planMentionDto.getContent();

		if (title == null || userid == null || content == null || "".equals(title) || "".equals(userid)
				|| "".equals(content)) { // null 체크
			map.put("msg", "제목, 내용은 비어 있을 수 없습니다.");
			return map;
		}

		try {
			PlanVo plan = new PlanVo();

			plan.setContentid(contentid);
			plan.setTitle(title);
			plan.setUserid(userid);
			plan.setContent(content);

			planMapper.planUpdate(plan);

		} catch (Exception e) {
			map.put("msg", "여행 계획 오류");
		}

		return map;
	}

}
