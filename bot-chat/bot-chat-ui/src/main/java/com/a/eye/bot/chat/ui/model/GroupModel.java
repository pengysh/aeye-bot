package com.a.eye.bot.chat.ui.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GroupModel {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "目的", required = true)
	private String purpose;

	@ApiModelProperty(value = "公有/私有", required = true)
	private Boolean publicOrPrivate;

	@ApiModelProperty(value = "用户", required = true)
	private String userJson;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Boolean getPublicOrPrivate() {
		return publicOrPrivate;
	}

	public void setPublicOrPrivate(Boolean publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}

	public String getUserJson() {
		return userJson;
	}

	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}
}
