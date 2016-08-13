package com.ai.bot.common.timeslot.message;

import com.ai.bot.common.message.BotReqMessage;

public class ReserveMessage extends BotReqMessage {

	private String id;

	public ReserveMessage(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
