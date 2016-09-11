package com.a.eye.bot.time.message;

import com.a.eye.bot.common.message.BotReqMessage;

import java.util.Date;

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
