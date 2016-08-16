package com.a.eye.bot.common.message.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageIdGenerate {

	private static Logger logger = LogManager.getLogger(MessageIdGenerate.class.getName());

	private static final IdWorker worker = new IdWorker(1, 1);

	public static String generate(String forwardMessageId, String receiverName) {
		String messageId = forwardMessageId + "-" + receiverName + worker.nextId();
		logger.debug("生成消息编号：" + messageId);
		return messageId;
	}

	public static Long generateFirstMessageId() {
		Long messageId = worker.nextId();
		logger.debug("生成消息编号：" + messageId);
		return messageId;
	}
}
