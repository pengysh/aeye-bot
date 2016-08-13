package com.a.eye.bot.user.ui.model;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserLoginModel {

	@ApiModelProperty(value = "用户标示（邮箱号、手机号、注册账号等等）", required = true)
	@Size(min = 1, max = 20, message = "user.userCode.length.error")
	private String userCode;

	@ApiModelProperty(value = "密码", required = true)
	@Size(min = 1, max = 19, message = "user.password.notnunll")
	private String password;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
