package com.ai.bot.common.timeslot.message;

import com.ai.bot.common.message.BotAckMessage;

public class ReserveAckMessage extends BotAckMessage {

	private String id;

	private boolean isOk;

	public ReserveAckMessage(String id, boolean isOk) {
		this.isOk = isOk;
		this.id = id;
	}

	public boolean isOk() {
		return isOk;
	}

	public String getId() {
		return id;
	}
}
