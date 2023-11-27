package com.ssafy.web.mention.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.common.model.service.CommonService;
import com.ssafy.web.mention.model.MentionVo;
import com.ssafy.web.mention.model.service.MentionService;
import com.ssafy.web.util.MyException;

@RestController
@RequestMapping("/mention")
public class MentionController {

	@Autowired
	MentionService mentionService;

	@Autowired
	private CommonService commonService;

	public MentionController(MentionService mentionService) {
		super();
		this.mentionService = mentionService;
	}

	@GetMapping("list")
	public Map<String, Object> list(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		HttpSession session = request.getSession(false);

		if (session != null) {
			String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용

			try {
				List<MentionVo> list = mentionService.mentionList(userid);

				if (list != null) {
					map.put("mentionList", list);
					map.put("msg", "mentionList OK");
				} else {
					map.put("msg", "알림이 없습니다.");
				}

			} catch (Exception e) {
				map.put("msg", "mentionList fail");
			}
		} else {
			map.put("msg", "로그인 해주세요.");
		}

		return map;
	}

	@PostMapping("update")
	public Map<String, String> update(@RequestBody Map<String, Object> reqMap, HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();

		HttpSession session = request.getSession(false);

		if (session != null) {
			String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용

			try {
				String contentid = (String) reqMap.get("contentid");

				MentionVo mention = new MentionVo();
				mention.setChecked(true);
				mention.setContentid(contentid);
				mention.setUserid(userid);

				System.out.println(mention);

				try {
					mentionService.mentionUpdate(mention);

					map.put("msg", "mentionUpdate OK");
				} catch (MyException e) {
					map.put("msg", "mentionUpdate Fail");
				}

			} catch (Exception e) {
				map.put("msg", "mentionUpdate fail");
			}

		} else {
			map.put("msg", "mentionUpdate fail");
		}

		return map;
	}

}
