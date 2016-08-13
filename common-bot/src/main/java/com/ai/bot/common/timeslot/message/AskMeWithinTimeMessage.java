package com.ai.bot.common.timeslot.message;

import java.util.Date;

import com.ai.bot.common.message.BotReqMessage;

public class AskMeWithinTimeMessage extends BotReqMessage {

	private Date startTime;
	private Date endTime;

	public AskMeWithinTimeMessage(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
}
