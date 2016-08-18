package com.a.eye.bot.chat.service.entity;

public class UserGroup {

	private Long userId;

	private String groups;

	public UserGroup(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserChatGroup [userId=" + userId + ", groups=" + groups + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
}
