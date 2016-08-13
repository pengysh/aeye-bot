package com.a.eye.bot.user.ui.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserRegisterModel {

	@ApiModelProperty(value = "姓名", required = true)
	@Size(min = 1, max = 20, message = "user.name.length.error")
	private String name;

	@ApiModelProperty(value = "邮箱", required = true)
	@Size(min = 1, max = 50, message = "user.email.notnull")
	@Email(message = "user.email.pattern.error")
	private String email;

	@ApiModelProperty(value = "密码", required = true)
	@Size(min = 1, max = 19, message = "user.password.notnunll")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
