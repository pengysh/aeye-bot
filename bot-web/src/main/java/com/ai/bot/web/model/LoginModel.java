package com.ai.bot.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class LoginModel {

	@ApiModelProperty(value = "注册账号编号", required = true)
	private Long id;

	@ApiModelProperty(value = "所属公司编号", required = true)
	private Long companyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}