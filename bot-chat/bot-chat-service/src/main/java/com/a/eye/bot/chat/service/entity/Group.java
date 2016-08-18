package com.a.eye.bot.chat.service.entity;

public class Group {

	private String _id;

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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
