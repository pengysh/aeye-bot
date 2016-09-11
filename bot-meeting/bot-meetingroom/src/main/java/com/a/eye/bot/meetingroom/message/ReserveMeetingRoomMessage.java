package com.a.eye.bot.meetingroom.message;

import com.a.eye.bot.common.message.BotReqMessage;

public class ReserveMeetingRoomMessage extends BotReqMessage {

	private String id;

	public ReserveMeetingRoomMessage(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
