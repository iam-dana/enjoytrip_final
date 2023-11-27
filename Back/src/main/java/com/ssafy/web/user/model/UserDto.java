package com.ssafy.web.user.model;

public class UserDto {
	private String userid, userpassword, username, email, rrn, pwdregisttime;
	private int age;

	public String getPwdregisttime() {
		return pwdregisttime;
	}

	public void setPwdregisttime(String pwdregisttime) {
		this.pwdregisttime = pwdregisttime;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public UserDto() {
		super();
	}

	@Override
	public String toString() {
		return "UserDto [userid=" + userid + ", userpassword=" + userpassword + ", username=" + username + ", email="
				+ email + ", rrn=" + rrn + ", pwdregisttime=" + pwdregisttime + ", age=" + age + "]";
	}
}
