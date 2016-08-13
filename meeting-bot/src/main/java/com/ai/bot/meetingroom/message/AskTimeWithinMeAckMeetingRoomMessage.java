package com.ai.bot.meetingroom.message;

import com.ai.bot.common.message.BotAckMessage;

public class AskTimeWithinMeAckMeetingRoomMessage extends BotAckMessage {

	private String id;
	private boolean canBeUse;

	public AskTimeWithinMeAckMeetingRoomMessage(String id, boolean canBeUse) {
		this.id = id;
		this.canBeUse = canBeUse;
	}

	public String getId() {
		return id;
	}

	public boolean isCanBeUse() {
		return canBeUse;
	}
}
