package com.ssafy.web.user.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.config.SessionConfig;
import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.user.model.UserDto;
import com.ssafy.web.user.service.OAuthService;
import com.ssafy.web.user.service.UserService;

@RestController
@RequestMapping("user")
//@CrossOrigin(origins = { "http://localhost:5173" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	OAuthService oAuthService;

//	private static boolean isNotRobot = false;

	@Value("${googleKey}")
	private String googlekey;

	@PostMapping("isrobot")
	public void isRobot(@RequestBody Map response, HttpServletRequest request) {
		String rResponse = (String) response.get("rResponse");
		if ("만료".equals(rResponse)) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute("isNotRobot");
			}
		} else {
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL("https://www.google.com/recaptcha/api/siteverify")
						.openConnection();
				String params = "secret=" + googlekey + "&response=" + rResponse;
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(params);
				wr.flush();
				wr.close();

				// 결과코드 확인(200 : 성공)
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					System.out.println("너 로봇 아니네!!!!");
					HttpSession session = request.getSession();
					session.setAttribute("isNotRobot", true);
				} else { // 결과코드 실패.
					HttpSession session = request.getSession(false);
					if (session != null) {
						session.removeAttribute("isNotRobot");
					}
				}
			} catch (Exception e) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("isNotRobot");
				}
			}
		}
	}

	@GetMapping("islogin")
	public Map isLogin(HttpServletRequest request) {
		Map<String, Object> map = new HashMap();
		HttpSession session = request.getSession(false);
		if (session != null) {
			String userid = (String) session.getAttribute("userid");
			if (userid != null) {
				map.put("isLogin", true);
				return map;
			} else {
				map.put("isLogin", false);
				return map;
			}
		} else {
			map.put("isLogin", false);
			return map;
		}

	}

	@PostMapping("checkcurrentemail")
	public Map checkEmail(@RequestBody UserDto u, HttpServletRequest request) {
		Map<String, Object> map = new HashMap();
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		if (userid != null) {
			u.setUserid(userid);
			UserDto m = userService.checkEmail(u);
			if (m != null) {
				map.put("isChecked", true);
				return map;
			} else {
				map.put("isChecked", false);
				return map;
			}
		} else {
			map.put("msg", "잘못된 접근입니다.");
			return map;
		}

	}

	@PostMapping("deleteuser")
	public Map deleteuser(@RequestBody UserDto u, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap();
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		if (userid != null) {
			u.setUserid(userid);
			try {
				String msg = userService.deleteUser(u);
				map.put("msg", msg);
				session.invalidate();
			} catch (Exception e) {
				map.put("msg", "회원 탈퇴 오류");
			}

		} else {
			map.put("msg", "다시 로그인 해주세요");
		}
		return map;
	}

	@PostMapping("changeuserinfo")
	public Map changeuserinfo(@RequestBody UserDto u, HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		if (userid != null) {
			u.setUserid(userid);
			userService.changeuserinfo(u);
			map.put("msg", "이메일 바꾸기 성공.");
			return map;
		} else {
			map.put("msg", "이메일 바꾸기 실패.");
			return map;
		}
	}

	@GetMapping("kakaologin")
	public Map kakaologin(@RequestParam String code, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> map = new HashMap();
		String access_Token = oAuthService.getKaKaoAccessToken(code);
		String email = oAuthService.createKakaoUser(access_Token);

		// 로봇인지 확인하는 세션 attribute.
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("isNotRobot") != null) {
				boolean isNotRobot = (boolean) session.getAttribute("isNotRobot");
				if (isNotRobot) {
					if (email != null) {
//						db로 가서 해당 이메일이 있는 사람이 있는지?
						String userid = userService.loginByKakao(email);
						if (userid != null) { // 회원이 있으면
							session.setAttribute("userid", userid);
							session.removeAttribute("isNotRobot");
							map.put("userid", userid);
							response.sendRedirect("/");
							return map;
						} else { // 회원이 없음
							map.put("msg", "회원가입이 필요합니다");
							response.sendRedirect("/");
							return map;
						}
					} else {
						map.put("msg", "로그인 실패");
						response.sendRedirect("/");
						return map;
					}
				} else { // 로봇임.
					map.put("msg", "사람입니까?");
					response.sendRedirect("/");
					return map;
				}
			} else {
				map.put("msg", "사람입니까?");
				response.sendRedirect("/");
				return map;
			}
		} else {
			map.put("msg", "사람입니까?");
			response.sendRedirect("/");
			return map;
		}
	}

	@PostMapping("idcheck") // 아이디 중복 검사.
	public boolean idCheck(@RequestBody UserDto u) {
		int isExist = userService.idCheck(u.getUserid());
		System.out.println(isExist);
		if (isExist > 0) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping("logout")
	public Map logout(HttpServletRequest request) {
		Map<String, String> map = new HashMap();
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		if (userid != null) {
			session.invalidate();
			map.put("msg", "로그아웃 되었습니다.");
			return map;
		} else {
			map.put("msg", "잘못된 접근입니다.");
			return map;
		}
	}

	@PostMapping("login")
	public Map login(@RequestBody UserDto m, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap();
		// 로봇인지 아닌 지 검사한 정보를 담은 session.
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("isNotRobot") != null) {
				boolean isNotRobot = (boolean) session.getAttribute("isNotRobot");
				System.out.println(isNotRobot);
				if (isNotRobot) {
					LoginHistoryDto lhd = new LoginHistoryDto();
					lhd.setUserid(m.getUserid());
					lhd.setUserip(request.getRemoteAddr());
					lhd.setLastfailedlogin(LocalDateTime.now().toString());
					try {
						map = userService.login(m, lhd);
						String username = map.get("username");
						if (username != null) { // 로그인 성공
							String userJSessionId = SessionConfig.sessionIdCheck("userid", m.getUserid());

							System.out.println("sessionconfig : " + userJSessionId + " : "+(userJSessionId == ""));
							// userJSessionId == "" 이렇게 하면 처음 로그인 할 때는 true 나옴

							session = request.getSession(false);
							session.setAttribute("userid", m.getUserid());
							session.removeAttribute("isNotRobot"); // 로봇 인증 지워줌.
							m.setUsername(username);
							map.put("userid", m.getUserid());
						} else { // 로그인 실패
							String msg = map.get("msg");
							map.put("msg", msg);
							session.removeAttribute("isNotRobot"); // 로봇 인증 지워줌.
						}
					} catch (Exception e) {
						map.put("msg", "사용자 인증 실패");
						e.printStackTrace();
						session.removeAttribute("isNotRobot"); // 로봇 인증 지워줌.
					}
//					System.out.println(map);
					return map;
				} else {
					map.put("msg", "사람입니까?");
					session.removeAttribute("isNotRobot"); // 로봇 인증 지워줌.
					return map;
				}
			} else {
				map.put("msg", "사람입니까?");
				session.removeAttribute("isNotRobot"); // 로봇 인증 지워줌.
				return map;
			}
		} else {
			map.put("msg", "사람입니까?");
			return map;
		}
	}

	@PostMapping("signup")
	public Map signup(@RequestBody UserDto m) {
		Map<String, String> map = new HashMap();
		LocalDateTime now = LocalDateTime.now();
		// plusYear: 패스워드 만료 시뮬레이션을 위한 것. 나중에 반드시 수정.
		m.setPwdregisttime(now.toString());
		System.out.println(m);
		try { 
			boolean result = userService.signUp(m);
			if (result) {
				map.put("msg", "회원가입이 완료되었습니다.");
			} else {
				map.put("msg", "중복된 아이디 입니다.");
			}

		} catch (Exception e) {
			map.put("msg", "error");
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("changepassword")
	public Map changePassword(@RequestBody Map<String, String> reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("userid");
		Map<String, String> map = new HashMap();

		if (userid != null) {
			UserDto userDto = new UserDto();
			userDto.setUserid(userid);
			userDto.setUserpassword(reqMap.get("curPw"));
			System.out.println(userDto + " " + reqMap.get("newPw"));
			
			try {
				map = userService.changePassword(userDto, reqMap.get("newPw"));
				session.invalidate();
			} catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "비밀번호 변경 실패");
				session.invalidate();
			}
		} else {
			map.put("msg", "올바른 접근이 아닙니다.");
		}
		return map;
	}

}
