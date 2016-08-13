package com.a.eye.bot.todo.ui.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DrogToDoItemModel {
	@ApiModelProperty(value = "任务项编号", required = true)
	private Long id;

	@ApiModelProperty(value = "任务项被移动的天数", required = true)
	private Integer dayOffset;

	@ApiModelProperty(value = "任务项被移动的小时数", required = true)
	private Integer hourOffset;

	@ApiModelProperty(value = "任务项被移动的分钟数", required = true)
	private Integer minuteOffset;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDayOffset() {
		return dayOffset;
	}

	public void setDayOffset(Integer dayOffset) {
		this.dayOffset = dayOffset;
	}

	public Integer getHourOffset() {
		return hourOffset;
	}

	public void setHourOffset(Integer hourOffset) {
		this.hourOffset = hourOffset;
	}

	public Integer getMinuteOffset() {
		return minuteOffset;
	}

	public void setMinuteOffset(Integer minuteOffset) {
		this.minuteOffset = minuteOffset;
	}
}
