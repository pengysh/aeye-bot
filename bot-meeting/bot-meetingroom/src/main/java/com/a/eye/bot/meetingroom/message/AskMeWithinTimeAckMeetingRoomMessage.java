package com.a.eye.bot.meetingroom.message;


import com.a.eye.bot.common.message.BotAckMessage;

public class AskMeWithinTimeAckMeetingRoomMessage extends BotAckMessage {

	private String id;

	private String name;

	public AskMeWithinTimeAckMeetingRoomMessage(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
