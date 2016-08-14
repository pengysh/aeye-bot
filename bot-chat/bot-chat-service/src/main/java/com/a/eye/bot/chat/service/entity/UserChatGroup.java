package com.a.eye.bot.chat.service.entity;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserChatGroup {

	private Gson gson = new Gson();

	private Long uesrId;

	private String groups;

	public UserChatGroup(Long uesrId) {
		this.uesrId = uesrId;
	}

	@Override
	public String toString() {
		return "UserChatGroup [id=" + uesrId + ", groups=" + groups + "]";
	}

	public List<UserChatGroupContent> getGroup() {
		return gson.fromJson(groups, new TypeToken<List<UserChatGroupContent>>() {
		}.getType());
	}
}
