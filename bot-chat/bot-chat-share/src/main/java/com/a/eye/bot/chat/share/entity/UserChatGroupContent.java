package com.a.eye.bot.chat.share.entity;

import com.google.gson.JsonArray;

public class UserChatGroupContent {

	private String groupId;

	private String groupTitle;

	private Integer memberCount;

	private JsonArray headImages = new JsonArray();

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public JsonArray getHeadImages() {
		return headImages;
	}

	public void setHeadImages(JsonArray headImages) {
		this.headImages = headImages;
	}
}
