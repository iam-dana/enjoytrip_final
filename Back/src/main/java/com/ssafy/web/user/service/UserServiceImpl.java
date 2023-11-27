package com.ssafy.web.user.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.loginhistory.service.LoginHistoryService;
import com.ssafy.web.sec.model.SecDto;
import com.ssafy.web.sec.model.mapper.SecMapper;
import com.ssafy.web.user.model.UserDto;
import com.ssafy.web.user.model.mapper.UserMapper;
import com.ssafy.web.util.OpenCrypt;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("secMapper")
	private SecMapper secMapper;

	@Autowired
	private LoginHistoryService loginHistoryService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean signUp(UserDto userDto) throws Exception {
		// TODO : 아이디 중복 체크
		String userName = getUserNameById(userDto.getUserid());
		if (userName != null) {
			return false;
		}

		// 비밀번호 유효성 검사
		String userpw = userDto.getUserpassword();
		if (!isValidatePw(userpw)) {
			return false;
		}

		// SecDto
		byte[] key = OpenCrypt.generateKey("AES", 128);

		SecDto secDto = new SecDto();

		secDto.setUserid(userDto.getUserid());
		secDto.setSalt(UUID.randomUUID().toString());
		secDto.setSeckey(OpenCrypt.byteArrayToHex(key));

		// 비밀번호 해싱
		String safePassword = OpenCrypt.getSHA256(userDto.getUserpassword(), secDto.getSalt());
		userDto.setUserpassword(safePassword); // 암호화된 비밀번호로 업데이트
		// 주민등록 암호화
		String[] encrypted = OpenCrypt.aesEncrypt(userDto.getRrn(), key);
		userDto.setRrn(encrypted[0]);
		secDto.setIv(encrypted[1]);

		userMapper.signup(userDto);
		System.out.println(secDto);
		secMapper.insertSecurity(secDto);

		return true;
	}

	public boolean isValidatePw(String userpw) { // 비밀번호 검사하는 함수
		// 비밀번호 최소 8자 최대 20자 + 영어, 숫자, 특수문자 포함 정규식
		final String check = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$";

		if (userpw == null || "".equals(userpw)) { // null 체크
			return false;
		}

		int pwLen = userpw.length();

		if (pwLen > 20 || pwLen < 8) { // 길이 체크
			return false;
		}

		Matcher matcher = Pattern.compile(check).matcher(userpw); // 정규식 만족 체크
		return matcher.matches(); // 만족하면 true 아니면 false
	}

	@Override
	public String getUserNameById(String userid) throws Exception {
		return userMapper.getUserNameById(userid);

	}

	public Map login(UserDto userDto, LoginHistoryDto lhd) throws Exception {
		Map<String, Object> map = new HashMap();

		// SALT 가져오기
		String salt = secMapper.readSalt(userDto.getUserid());
		String safePassword = OpenCrypt.getSHA256(userDto.getUserpassword(), salt);
		if (!safePassword.equals(getUserPassword(userDto.getUserid()))) { // 복호화된 비밀번호와 userid해당하는 비밀번호 일치 확인
			// 불일치 한다면
			int failType = loginHistoryService.failLogin(lhd);
			if (failType < 0) {
				// 대기시간이 있는 경우
				map.put("msg", -failType + "초 후에 다시 시도해주세요.");
				return map;
			} else {
				// 남은 시도횟수가 있는 경우
				map.put("msg", "남은 로그인 시도 횟수: " + failType);
				return map;
			}
		} else {
			// 일치하는 경우
			// failed login login history check
			userDto.setUserpassword(safePassword);
			UserDto u = userMapper.login(userDto);
			loginHistoryService.successLogin(userDto, lhd);

			// 비밀번호 만료 기간 지났는지 검사.
			LocalDateTime lastLogin = LocalDateTime.parse(u.getPwdregisttime());
			LocalDateTime now = LocalDateTime.now();
			long diff = ChronoUnit.DAYS.between(lastLogin, now);
			System.out.println("경과 시간: " + diff);
			if (diff >= 90) { // 비밀번호 유효시간 지났을 경우
				map.put("msg", "needPwdChange");
				map.put("userid", userDto.getUserid());
				map.put("username", u.getUsername());

				System.out.println(userDto.getUserid());

				return map;
			} else { // 남았을 경우(로그인 성공)
				map.put("username", u.getUsername());
				return map;
			}
		}

	}

	public String getUserPassword(String userid) throws Exception {
		return userMapper.getUserPassword(userid);
	}

	@Override
	public Map changePassword(UserDto userDto, String newPw) throws Exception {
		Map<String, String> map = new HashMap();

		// userDto 에 저장된 아이디와 패스워드가 디비에 저장된 것과 일치하는 지 확인해야함.
		String cur_pwd = userDto.getUserpassword(); // ㅋㅋ

		// SALT 가져오기
		String salt = secMapper.readSalt(userDto.getUserid()); // 해당 아이디에 맞는 솔트값 가져옴.
		String curPassword = OpenCrypt.getSHA256(cur_pwd, salt); // 현재 비밀번호 해시
//		System.out.println(curPassword.equals(getUserPassword(userDto.getUserid())));
		

		if (curPassword.equals(getUserPassword(userDto.getUserid()))) { // 현재 비밀번호가 맞았으면
			if (isValidatePw(newPw)) { // 새 비밀번호 유효성 검사
				// SecDto
				String tmpNewPwd = OpenCrypt.getSHA256(newPw, salt);
				if (tmpNewPwd.equals(getUserPassword(userDto.getUserid()))) {
					// 같은 비밀번호로 변경하려고한다면
					map.put("msg", "동일한 비밀번호는 사용할 수 없습니다.");
				} else {
					SecDto secDto = new SecDto();
					secDto.setUserid(userDto.getUserid());
					secDto.setSalt(UUID.randomUUID().toString());

					// 비밀번호 해싱
					String safePassword = OpenCrypt.getSHA256(newPw, secDto.getSalt()); // 새로운 비밀번호 해시
					userDto.setUserpassword(safePassword); // 암호화된 비밀번호로 업데이트
					userMapper.changePassword(userDto);
					secMapper.updateSalt(secDto);
					map.put("msg", "비밀번호 변경 성공");
				}
			} else {
				map.put("msg", "새로운 비밀번호가 유효하지 않습니다.");
			}

		} else {
			map.put("msg", "현재 비밀번호가 일치하지 않습니다.");
		}

		return map;
	}

	@Override
	public String deleteUser(UserDto u) throws Exception {
		UserDto m = checkUser(u);
		System.out.println("확인 ㅡㅡ" + m);
		String username = m.getUsername();
		if (username != null) {
			loginHistoryService.delete(u.getUserid());
			userMapper.delete(u.getUserid());
			secMapper.delete(u.getUserid());
			return "회원 탈퇴 성공";
		} else {
			return "비밀 번호 다시 입력";
		}
	}

	@Override
	public int idCheck(String userid) {
		return userMapper.idCheck(userid);
	}

	@Override
	public void changeuserinfo(UserDto u) {
		userMapper.changeuserinfo(u);
	}

	@Override
	public UserDto checkUser(UserDto u) throws Exception {
		String salt = secMapper.readSalt(u.getUserid());
		String safePassword = OpenCrypt.getSHA256(u.getUserpassword(), salt);
		u.setUserpassword(safePassword);
		UserDto userDto = userMapper.login(u);
//		System.out.println("로그인 됐나? " + userDto);
		return userDto;
	}

	@Override
	public UserDto checkEmail(UserDto u) {
		return userMapper.checkEmail(u);
	}

	@Override
	public String loginByKakao(String email) {
		return userMapper.loginByKakao(email);
	}

	@Override
	public List<String> userList() throws Exception {
		return userMapper.userList();
	}

}
