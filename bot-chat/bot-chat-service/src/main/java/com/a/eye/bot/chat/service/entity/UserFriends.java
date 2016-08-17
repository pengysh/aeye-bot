package com.a.eye.bot.chat.service.entity;

public class UserFriends {
	private Long userId;

	private String friends;

	public UserFriends(Long userId) {
		this.userId = userId;
	}

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

	@Override
	public String toString() {
		return "UserFriends [userId=" + userId + ", friends=" + friends + "]";
	}
}