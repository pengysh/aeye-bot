package com.a.eye.bot.time.message;

import com.a.eye.bot.common.message.BotReqMessage;

public class ReserveMessage extends BotReqMessage {

	private String id;

	public ReserveMessage(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
