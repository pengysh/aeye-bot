package com.ai.bot.common.timeslot.message;

import com.ai.bot.common.message.BotAckMessage;

public class AskTimeWithinMeAckMessage extends BotAckMessage {

	private String id;
	
	private boolean isOk;

	public AskTimeWithinMeAckMessage(String id, boolean isOk) {
		this.id = id;
		this.isOk = isOk;
	}

	public String getId() {
		return id;
	}

	public boolean isOk() {
		return isOk;
	}
}
