package com.a.eye.bot.chat.service.entity;

public class UserState {

	private Long userId;

	private String state;

	public UserState(Long userId, String state) {
		this.userId = userId;
		this.state = state;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "UserState [userId=" + userId + ", state=" + state + "]";
	}
}
