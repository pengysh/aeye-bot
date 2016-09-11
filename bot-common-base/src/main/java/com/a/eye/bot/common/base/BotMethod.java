package com.a.eye.bot.common.base;

import com.a.eye.bot.common.message.BotReqMessage;

public interface BotMethod {
	public void run(String reverseMessageId, BotReqMessage message, String reverseSender);
}
