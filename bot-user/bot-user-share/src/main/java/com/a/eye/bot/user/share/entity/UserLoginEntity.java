package com.a.eye.bot.user.share.entity;

import java.io.Serializable;

public class UserLoginEntity implements Serializable{

	private static final long serialVersionUID = 8236304744116557959L;
	private Long userId;
	private String userName;
	private Long companyId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
