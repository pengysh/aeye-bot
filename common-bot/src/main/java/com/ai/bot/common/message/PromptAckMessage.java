package com.ai.bot.common.message;

public class PromptAckMessage extends BotAckMessage {

	private String promptContent;

	public PromptAckMessage(String promptContent) {
		this.promptContent = promptContent;
	}

	public String getPromptContent() {
		return promptContent;
	}
}
