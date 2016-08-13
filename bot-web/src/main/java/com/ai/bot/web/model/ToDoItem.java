package com.ai.bot.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ToDoItem {
	@ApiModelProperty(value = "任务项编号", required = true)
	private String itemId;

	@ApiModelProperty(value = "所属账号", required = true)
	private String accountId;

	@ApiModelProperty(value = "任务描述", required = true)
	private String title;

	@ApiModelProperty(value = "任务开始时间（年月日）", required = true)
	private String startDate;

	@ApiModelProperty(value = "任务开始时间（时分秒）", required = true)
	private String startTime;

	@ApiModelProperty(value = "任务结束时间（年月日）", required = true)
	private String endDate;

	@ApiModelProperty(value = "任务结束时间（时分秒）", required = true)
	private String endTime;
	
	@ApiModelProperty(value = "任务项状态", required = false)
	private String state;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
