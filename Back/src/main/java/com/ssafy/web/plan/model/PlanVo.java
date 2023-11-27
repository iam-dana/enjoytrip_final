package com.ssafy.web.plan.model;

public class PlanVo {

	// TODO: set 메소드 입력 데이터 유효성 체크

	private String contentid;
	private String title;
	private String userid;
	private String content;
	private String timestamp;

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "PlanVo [contentid=" + contentid + ", title=" + title + ", userid=" + userid + ", content=" + content
				+ ", timestamp=" + timestamp + "]";
	}

}
