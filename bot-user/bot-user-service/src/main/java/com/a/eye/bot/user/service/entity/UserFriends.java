package com.a.eye.bot.user.service.entity;

public class UserFriends {
	private Long userId;

	private String friends;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends == null ? null : friends.trim();
	}
}