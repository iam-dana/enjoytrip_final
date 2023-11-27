package com.ssafy.web.plan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.common.model.service.CommonService;
import com.ssafy.web.mention.model.MentionVo;
import com.ssafy.web.mention.model.service.MentionService;
import com.ssafy.web.plan.model.PlanMentionDto;
import com.ssafy.web.plan.model.PlanVo;
import com.ssafy.web.plan.model.service.PlanService;
import com.ssafy.web.user.service.UserService;
import com.ssafy.web.util.JwtTokenProvider;
import com.ssafy.web.util.MyException;

@RestController
@RequestMapping("/plan")
public class PlanController {

	@Autowired
	private PlanService planService;

	@Autowired
	private MentionService mentionService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserService userService;

	public PlanController(PlanService planService, MentionService mentionService) {
		super();
		this.planService = planService;
		this.mentionService = mentionService;
	}

	@GetMapping("regist")
	public Map<String, Object> regist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		HttpSession session = request.getSession(false); // 세션 받고

		if (session != null) { // 로그인 중이라면
			String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용
			map.put("userid", userid);
			try {
				List<String> userList = userService.userList();
				map.put("userList", userList);
			} catch (Exception e) {
				map.put("msg", "계획 작성 오류");
			}
		} else {
			map.put("msg", "로그인 시에만 여행 계획을 작성할 수 있습니다.");
		}

		return map;
	}

	@PostMapping("regist")
	public Map<String, String> regist(@RequestBody Map<String, Object> reqMap, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		HttpSession session = request.getSession(false); // 세션 받고

		if (session != null) { // 로그인 중이라면
			try {
				String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용

				String title = (String) reqMap.get("title");
				String content = (String) reqMap.get("content");
				String contentid = UUID.randomUUID().toString(); // 랜덤한 uuid 값으로 contend id 설정
				ArrayList<String> tmplist = (ArrayList<String>) reqMap.get("mentionedid");
				String[] mentionedId = tmplist.toArray(new String[tmplist.size()]);

				PlanMentionDto planMentionDto = new PlanMentionDto();
				planMentionDto.setContentid(contentid);
				planMentionDto.setContent(content);
				planMentionDto.setTitle(title);
				planMentionDto.setMentionedid(mentionedId);
				planMentionDto.setUserid(userid);

				map = planService.planRegist(planMentionDto);

				if (!map.containsKey("msg")) {
					map = mentionService.mentionRegist(planMentionDto);
				}

				if (!map.containsKey("msg")) {
					map.put("msg", "여행 계획 작성 완료!");
				}

			} catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "등록 오류");
			}
		} else {
			map.put("msg", "로그인 시에만 여행 계획을 작성할 수 있습니다.");
		}
		
		return map;
	}

	@GetMapping("list")
	public Map<String, Object> list() {
		Map<String, Object> map = new HashMap<>();

		try {
			List<PlanVo> list = planService.planList();

			if (list != null) { // null 체크
				map.put("planList", list);
				map.put("msg", "planList OK");
			} else {
				map.put("msg", "여행 계획이 비어있습니다.");
			}

		} catch (MyException e) {
			map.put("msg", "planList fail");
		}

		return map;
	}

	@GetMapping("view/{contentid}")
	public Map<String, String> view(@PathVariable String contentid) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			PlanVo planToView = planService.planView(contentid);

			map.put("contentid", planToView.getContentid());
			map.put("title", planToView.getTitle());
			map.put("userid", planToView.getUserid());
			map.put("content", planToView.getContent());
			map.put("timestamp", planToView.getTimestamp());

			List<String> mentionList = mentionService.mentionView(contentid);

			map.put("mentionList", mentionList.toString());

			map.put("msg", "planView OK"); // 성공
		} catch (MyException e) {
			map.put("msg", "planView Fail"); // 실패
		}

		return map;
	}

	@GetMapping("update/{contentid}")
	public Map<String, Object> update(@PathVariable String contentid, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("contentid : " + contentid);

		HttpSession session = request.getSession(false);

		if (session != null) {
			try {
				PlanVo planToView = planService.planView(contentid);
				System.out.println(planToView);

				String userid = planToView.getUserid();
				String sessionUserid = session.getAttribute("userid").toString();

				if (userid.equals(sessionUserid)) {
					map.put("contentid", planToView.getContentid());
					map.put("title", planToView.getTitle());
					map.put("userid", userid);
					map.put("content", planToView.getContent());
					map.put("timestamp", planToView.getTimestamp());

					List<String> mentionList = mentionService.mentionView(contentid);

					map.put("mentionList", mentionList);

					map.put("msg", "planUpdateView ok");
				} else {
					map.put("msg", "자신의 여행 계획만 수정할 수 있습니다."); // 실패
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "planUpdateView Fail"); // 실패
			}
		} else {
			map.put("msg", "로그인 시에만 여행 계획을 수정할 수 있습니다.");
		}

		return map;
	}

	@PostMapping("update")
	public Map<String, String> update(@RequestBody Map<String, Object> reqMap, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		HttpSession session = request.getSession(false);

		if (session != null) {
			String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용
			String title = (String) reqMap.get("title");
			String content = (String) reqMap.get("content");
			String contentid = (String) reqMap.get("contentid");
			ArrayList<String> tmplist = (ArrayList<String>) reqMap.get("mentionedid");
			String[] mentionedId = tmplist.toArray(new String[tmplist.size()]);

			PlanMentionDto planMentionDto = new PlanMentionDto();
			planMentionDto.setUserid(userid);
			planMentionDto.setContentid(contentid);
			planMentionDto.setContent(content);
			planMentionDto.setTitle(title);
			planMentionDto.setMentionedid(mentionedId);

			try {
				map = planService.planUpdate(planMentionDto);

				mentionService.mentionDelete(contentid);

				if (!map.containsKey("msg")) {
					map = mentionService.mentionRegist(planMentionDto);
				}

				if (!map.containsKey("msg")) {
					map.put("msg", "여행 계획 수정 완료!");
				}

			} catch (Exception e) {
				map.put("msg", "계획 수정 실패");
			}

		} else {
			map.put("msg", "접근이 안되는 기능입니다");
		}

		return map;
	}

	@GetMapping("delete/{contentid}")
	public Map<String, String> delete(@PathVariable String contentid, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		HttpSession session = request.getSession(false);

		if (session != null) {
			String userid = session.getAttribute("userid").toString(); // 세션에 저장된 사용자 id를 가져와서 사용

			try {
				int delete = planService.planDelete(contentid, userid);
				if (delete == 0) {
					map.put("msg", "자신의 여행 계획만 삭제할 수 있습니다."); // 실패
				}
			} catch (Exception e) {
				map.put("msg", "여행 계획 삭제 실패");
			}

			try {
				mentionService.mentionDelete(contentid);
			} catch (Exception e) {
				map.put("msg", "여행 계획 삭제 실패"); // 실패
			}

			if (!map.containsKey("msg")) {
				map.put("msg", "여행 계획 삭제 성공"); // 성공
			}

		} else {
			map.put("msg", "로그인 시에만 여행 계획을 삭제할 수 있습니다.");
		}

		return map;
	}

}
