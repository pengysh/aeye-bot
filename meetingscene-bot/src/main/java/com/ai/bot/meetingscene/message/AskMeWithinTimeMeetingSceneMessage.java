package com.ai.bot.meetingscene.message;

import java.util.Date;

import com.ai.bot.common.message.BotReqMessage;

public class AskMeWithinTimeMeetingSceneMessage extends BotReqMessage {

	private String district;
	private Date startTime;
	private Date endTime;
	private int capacity;
	private boolean hasProjector;

	public AskMeWithinTimeMeetingSceneMessage() {
	}

	public AskMeWithinTimeMeetingSceneMessage(String district, Date startTime, Date endTime, int capacity, boolean hasProjector) {
		this.district = district;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.hasProjector = hasProjector;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getDistrict() {
		return district;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isHasProjector() {
		return hasProjector;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setHasProjector(boolean hasProjector) {
		this.hasProjector = hasProjector;
	}

}
