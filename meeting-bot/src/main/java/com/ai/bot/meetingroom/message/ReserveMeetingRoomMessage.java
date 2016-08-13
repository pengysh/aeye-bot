package com.ai.bot.meetingroom.message;

import com.ai.bot.common.message.BotReqMessage;

public class ReserveMeetingRoomMessage extends BotReqMessage {

	private String id;

	public ReserveMeetingRoomMessage(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
