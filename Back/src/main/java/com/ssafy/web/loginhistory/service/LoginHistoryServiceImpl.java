 package com.ssafy.web.loginhistory.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.web.user.model.UserDto;
import com.ssafy.web.util.MyException;

import com.ssafy.web.loginhistory.model.LoginHistoryDto;
import com.ssafy.web.loginhistory.model.mapper.LoginHistoryMapper;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {
	
	@Autowired
	private LoginHistoryMapper lhMapper;

	private static final int WAIT = 30; // 대기 시간(초)
	private static final int LIMIT = 5; // 재시도 제한 횟수

	@Override
	public int failLogin(LoginHistoryDto lhd) throws MyException {
		// 로그인 실패 횟수에 따라 남은 시도 횟수 반환
		// 최대 시도 횟수 넘겼고 대기 시간 안지났으면 -1 반환

		// 로그인 실패했을때 로그인 정보 담긴 유저 객체 받음
		String userid = lhd.getUserid();
		String userip = lhd.getUserip();
		LoginHistoryDto history = lhMapper.checkHistory(userid, userip);
		int failType = 0;

		if (history == null) {
			// 처음으로 로그인 시도한 상황, 로그인 기록 정보 추가
//			history = new LoginHistoryDto(userDto.getUserid(), "ㅋㅋ", LocalDateTime.now().toString());
			history = lhd;
			lhMapper.firstHistory(history);
			System.out.println(history);
			return LIMIT - 1;
		} else {
			// 로그인 실패 횟수에 따라 너무 많으면 몇초후에 다시 시도, 또는 남은 횟수 
			int retry = history.getRetry() + 1;
			history.setRetry(retry);
//			LocalDateTime lastLogin = LocalDateTime.parse(history.getLastfailedlogin());
			if (retry >= LIMIT) { // 횟수 넘었을 -> 대기 시간 지나면 업데이트
				// 대기 시간 확인
				LocalDateTime lastLogin = LocalDateTime.parse(history.getLastfailedlogin());
				LocalDateTime now = LocalDateTime.now();
				long diff = ChronoUnit.SECONDS.between(lastLogin, now);

				if (diff < WAIT) { // 대기 시간 안지남
					failType = -(WAIT - (int) diff);
				} else { // 대기 시간 지남
					history.setLastfailedlogin(LocalDateTime.now().toString());
					failType = -WAIT;
					lhMapper.deleteHistory(userid, userip);
				}
			} else { // 횟수 안넘음 -> 마지막 시간 업데이트
				history.setLastfailedlogin(LocalDateTime.now().toString());
				failType = LIMIT - retry;
			}

			lhMapper.updateHistoryFail(history);
			return failType;
		}
	}

	@Override
	public void successLogin(UserDto userDto, LoginHistoryDto lhd) throws MyException {
		// 로그인 성공시 로그인 성공시간 업데이트 및 리트라이 횟수 초기
		String userid = userDto.getUserid();
		String userip = lhd.getUserip();

		LoginHistoryDto history = lhMapper.checkHistory(userid, userip);
//		System.out.println("로그인 성공했는데"+history);

		if (history == null) {
			// 처음으로 로그인 시도한 상황, 로그인 기록 정보 추가
			history = new LoginHistoryDto();
			history.setUserid(userDto.getUserid());
			history.setUserip(userip);
			history.setRetry(0);
			lhMapper.firstHistory(history);
		} else {
			// 로그인 실패 횟수 초기화
			history = new LoginHistoryDto();
			history.setUserid(userid);
			history.setUserip(userip);
			history.setRetry(0);
//			System.out.println("초기화 해주세요"+history);
			lhMapper.updateHistorySuccess(history);
		}

	}

	@Override
	public void delete(String userid) {
		lhMapper.delete(userid);
	}

	@Override
	public void deleteHistory(String userid, String userip) {
		lhMapper.deleteHistory(userid, userip);
	}



}
