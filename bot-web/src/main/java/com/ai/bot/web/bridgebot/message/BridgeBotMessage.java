package com.ai.bot.web.bridgebot.message;

import com.ai.bot.common.message.BotReqMessage;

public class BridgeBotMessage extends BotReqMessage {

	private String content;

	public BridgeBotMessage(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
