package com.a.eye.bot.todo.ui.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ToDoItemDataModel {

	@ApiModelProperty(value = "待办事项ID", required = true)
	private Long id;

	@ApiModelProperty(value = "用户ID", required = true)
	private Long userId;

	@ApiModelProperty(value = "待办事项标题", required = true)
	private String title;

	@ApiModelProperty(value = "开始时间（年月日）", required = false)
	private String startDate;

	@ApiModelProperty(value = "开始时间（时分）", required = false)
	private String startTime;

	@ApiModelProperty(value = "结束时间（年月日）", required = false)
	private String endDate;

	@ApiModelProperty(value = "结束时间（时分）", required = false)
	private String endTime;

	@ApiModelProperty(value = "是否全天的待办事项", required = true)
	private boolean allDay;

	@ApiModelProperty(value = "重要级别", required = false)
	private String importance;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
