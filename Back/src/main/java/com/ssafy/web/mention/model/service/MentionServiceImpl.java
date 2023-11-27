package com.ssafy.web.mention.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.mention.model.MentionVo;
import com.ssafy.web.mention.model.mapper.MentionMapper;
import com.ssafy.web.plan.model.PlanMentionDto;
import com.ssafy.web.util.MyException;

@Service
public class MentionServiceImpl implements MentionService {

	@Autowired
	private MentionMapper mentionMapper;

	public MentionServiceImpl(MentionMapper mentionMapper) {
		super();
		this.mentionMapper = mentionMapper;
	}

	@Override
	public Map<String, String> mentionRegist(PlanMentionDto planMentionDto) throws MyException {
		Map<String, String> map = new HashMap<String, String>();
		
		String[] mentionedId = planMentionDto.getMentionedid();
		String contentid = planMentionDto.getContentid();
		
		if(mentionedId.length != 0 && mentionedId[0] != "") {
			try {
				MentionVo mentionVo = new MentionVo();

				mentionVo.setContentid(contentid);

				for (String menId : mentionedId) {
					mentionVo.setUserid(menId);
					mentionMapper.mentionRegist(mentionVo);
				}

			} catch (MyException e) {
				map.put("msg", "언급 오류");
			}
		}
		
		return map;
	}

	@Override
	public List<MentionVo> mentionList(String userid) throws MyException {
		return mentionMapper.mentionList(userid);
	}

	@Override
	public void mentionUpdate(MentionVo mention) throws MyException {
		mentionMapper.mentionUpdate(mention);
	}

	@Override
	public List<String> mentionView(String contentid) throws MyException {
		return mentionMapper.mentionView(contentid);
	}

	@Override
	public void mentionDelete(String contentid) throws MyException {
		mentionMapper.mentionDelete(contentid);
	}

}
