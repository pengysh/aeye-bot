package com.a.eye.bot.common.message.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageIdGenerate {

	private static Logger logger = LogManager.getLogger(MessageIdGenerate.class.getName());

	private static Long messageIdIncrement = new Long(0);

	private static synchronized Long increment() {
		messageIdIncrement++;
		return messageIdIncrement;
	}

	public static String generate(String forwardMessageId, String receiverName) {
		MessageIdGenerate.increment();
		String messageId = forwardMessageId + "-" + receiverName + String.valueOf(messageIdIncrement);
		logger.debug("生成消息编号：" + messageId);
		return messageId;
	}

	public static String generateFirstMessageId() {
		MessageIdGenerate.increment();
		String messageId = "Begin_" + String.valueOf(messageIdIncrement);
		logger.debug("生成消息编号：" + messageId);
		return messageId;
	}
}
