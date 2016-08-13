package com.ai.bot.common.timeslot.message;

import java.util.Date;

import com.ai.bot.common.message.BotAckMessage;

public class AskMeWithinTimeAckMessage extends BotAckMessage {

	private String id;
	private Date startTime;
	private Date endTime;
	private String state;

	public AskMeWithinTimeAckMessage(String id, Date startTime, Date endTime, String state) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getState() {
		return state;
	}
}
