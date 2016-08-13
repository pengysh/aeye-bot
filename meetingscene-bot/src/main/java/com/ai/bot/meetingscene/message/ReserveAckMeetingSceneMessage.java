package com.ai.bot.meetingscene.message;

import com.ai.bot.common.message.BotAckMessage;

public class ReserveAckMeetingSceneMessage extends BotAckMessage {

	private String replyMessage;

	public ReserveAckMeetingSceneMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getReplyMessage() {
		return replyMessage;
	}
}
