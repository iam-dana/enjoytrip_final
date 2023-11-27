package com.ssafy.web.mention.model;

public class MentionVo {
	private String userid, contentid, title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private boolean isChecked;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "MentionVo [userid=" + userid + ", contentid=" + contentid + ", title=" + title + ", isChecked="
				+ isChecked + "]";
	}

}
