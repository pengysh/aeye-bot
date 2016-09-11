package com.a.eye.bot.meetingroom.message;

import com.a.eye.bot.common.message.BotReqMessage;
import java.util.Date;


public class AskTimeWithinMeMeetingRoomMessage extends BotReqMessage {

	private String district;
	private Date startTime;
	private Date endTime;
	private int capacity;
	private boolean hasProjector;

	public AskTimeWithinMeMeetingRoomMessage(String district, Date startTime, Date endTime, int capacity, boolean hasProjector) {
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
