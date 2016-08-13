package com.ai.bot.meetingroom.message;

import com.ai.bot.common.message.BotAckMessage;

public class ReserveAckMeetingRoomMessage extends BotAckMessage {

	private String id;

	private boolean isOk;

	public ReserveAckMeetingRoomMessage(String id, boolean isOk) {
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
