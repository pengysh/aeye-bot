package com.ai.bot.meetingroom.message;

import java.util.Date;

import com.ai.bot.common.message.BotReqMessage;

public class AskMeWithinTimeMeetingRoomMessage extends BotReqMessage {

	private String district;
	private Date startTime;
	private Date endTime;
	private int capacity;
	private boolean hasProjector;

	public AskMeWithinTimeMeetingRoomMessage(String district, Date startTime, Date endTime, int capacity, boolean hasProjector) {
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
}
