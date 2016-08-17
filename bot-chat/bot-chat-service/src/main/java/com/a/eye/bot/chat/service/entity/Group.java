package com.a.eye.bot.chat.service.entity;

import com.google.gson.Gson;

public class Group {

	private Gson gson = new Gson();

	private String title;

	private Integer userCount;

	private String users;

	private String purpose;

	private boolean publicOrPrivate;
	
	public Group(String title, String purpose, boolean publicOrPrivate, String users, Integer userCount) {
		this.title = title;
		this.purpose = purpose;
		this.publicOrPrivate = publicOrPrivate;
		this.userCount = userCount;
		this.users = users;
	}

	@Override
	public String toString() {
		return "ChatGroup [ title=" + title + ",purpose=" + purpose + ",publicOrPrivate=" + publicOrPrivate + ", usercount=" + userCount + ", users=" + users + "]";
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public boolean isPublicOrPrivate() {
		return publicOrPrivate;
	}

	public void setPublicOrPrivate(boolean publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}
}
