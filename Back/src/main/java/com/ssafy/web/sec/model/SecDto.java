package com.ssafy.web.sec.model;

public class SecDto {

	String userid, salt, seckey, iv;

	public SecDto() {
		super();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSeckey() {
		return seckey;
	}

	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	@Override
	public String toString() {
		return "SecDto [userid=" + userid + ", salt=" + salt + ", seckey=" + seckey + ", iv=" + iv + "]";
	}

}
