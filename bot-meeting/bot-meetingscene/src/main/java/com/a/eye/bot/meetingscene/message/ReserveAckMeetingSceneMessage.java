package com.a.eye.bot.meetingscene.message;

import com.a.eye.bot.common.message.BotAckMessage;

public class ReserveAckMeetingSceneMessage extends BotAckMessage {

	private String replyMessage;

	public ReserveAckMeetingSceneMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getReplyMessage() {
		return replyMessage;
	}
}
