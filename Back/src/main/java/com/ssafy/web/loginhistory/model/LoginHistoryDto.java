package com.ssafy.web.loginhistory.model;

import java.time.LocalDateTime;
import java.util.Date;
 
public class LoginHistoryDto {

	private String userid, userip, lastfailedlogin;
	private int retry;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserip() {
		return userip;
	}

	public void setUserip(String userip) {
		this.userip = userip;
	}

	public String getLastfailedlogin() {
		return lastfailedlogin;
	}

	public void setLastfailedlogin(String lastfailedlogin) {
		this.lastfailedlogin = lastfailedlogin;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	@Override
	public String toString() {
		return "LoginHistoryDto [userid=" + userid + ", userip=" + userip + ", lastfailedlogin=" + lastfailedlogin
				+ ", retry=" + retry + "]";
	}

}