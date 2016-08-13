package com.ai.bot.common.base;

import com.ai.bot.common.message.BotReqMessage;

public interface BotMethod {
	public void run(String reverseMessageId, BotReqMessage message, String reverseSender);
}
