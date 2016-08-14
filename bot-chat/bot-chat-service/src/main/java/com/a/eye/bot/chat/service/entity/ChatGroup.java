package com.a.eye.bot.chat.service.entity;

import java.util.List;

import com.google.gson.Gson;

public class ChatGroup {

	private Gson gson = new Gson();

	private Long groupId;

	private String title;

	private Integer userCount;

	private String users;

	public ChatGroup(Long groupId, String title, List<Long> userIds) {
		this.groupId = groupId;
		this.title = title;
		this.userCount = userIds.size();
		this.users = gson.toJson(userIds);
	}

	@Override
	public String toString() {
		return "ChatGroup [groupId=" + groupId + ", title=" + title + ", usercount=" + userCount + ", users=" + users + "]";
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
}
