package com.a.eye.bot.chat.service.entity;

public class UserGroup {

	private Long uesrId;

	private String groups;
	
	public UserGroup(Long uesrId) {
		this.uesrId = uesrId;
	}

	@Override
	public String toString() {
		return "UserChatGroup [uesrId=" + uesrId + ", groups=" + groups + "]";
	}

	public Long getUesrId() {
		return uesrId;
	}

	public void setUesrId(Long uesrId) {
		this.uesrId = uesrId;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
}
