package com.ssafy.web.plan.model;

import java.util.Arrays;

public class PlanMentionDto {
	private String contentid;
	private String title;
	private String userid;
	private String content;
	private String timestamp;
	private String[] mentionedid;
	private boolean isChecked;

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

	public String[] getMentionedid() {
		return mentionedid;
	}

	public void setMentionedid(String[] mentionedid) {
		this.mentionedid = mentionedid;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "PlanMentionDto [contentid=" + contentid + ", title=" + title + ", userid=" + userid + ", content="
				+ content + ", timestamp=" + timestamp + ", mentionedid=" + Arrays.toString(mentionedid)
				+ ", isChecked=" + isChecked + "]";
	}

}
