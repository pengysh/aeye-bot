package com.a.eye.bot.meetingscene.message;

import com.a.eye.bot.common.message.BotReqMessage;

public class ReserveMeetingSceneMessage extends BotReqMessage {

	private String id;

	public ReserveMeetingSceneMessage(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
