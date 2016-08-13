package com.ai.bot.meetingscene.message;

import com.ai.bot.common.message.BotAckMessage;

public class AskMeWithinTimeAckMeetingSceneMessage extends BotAckMessage {

	private String replyMessage;

	public AskMeWithinTimeAckMeetingSceneMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getReplyMessage() {
		return replyMessage;
	}
}
