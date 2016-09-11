package com.a.eye.bot.meetingscene.message;


import com.a.eye.bot.common.message.BotAckMessage;

public class AskTimeWithinMeAckMeetingSceneMessage extends BotAckMessage {

	private String id;
	private boolean canBeUse;

	public AskTimeWithinMeAckMeetingSceneMessage(String id, boolean canBeUse) {
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
