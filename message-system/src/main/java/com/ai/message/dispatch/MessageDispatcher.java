package com.ai.message.dispatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.message.actor.ActorSystem;
import com.ai.message.channel.ActorChannel;
import com.google.gson.Gson;

public class MessageDispatcher {

	private Logger logger = LogManager.getLogger(this.getClass());

	private Gson gson = new Gson();

	public void dispatch(String reverseMessageId, String content) {
		String isReply = content.substring(0, content.indexOf("|"));
		content = content.substring(content.indexOf("|") + 1, content.length());
		String sender = content.substring(0, content.indexOf("|"));
		content = content.substring(content.indexOf("|") + 1, content.length());
		String messageClassName = content.substring(0, content.indexOf("|"));
		String messageStr = content.substring(content.indexOf("|") + 1, content.length());
		logger.debug("isReply:" + isReply);
		logger.debug("reverseMessageId:" + reverseMessageId);
		logger.debug("sender:" + sender);
		logger.debug("messageClassName:" + messageClassName);
		logger.debug("messageStr:" + messageStr);

		Class<?> messageClass = null;
		try {
			messageClass = Class.forName(messageClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Object message = gson.fromJson(messageStr, messageClass);
		String forwardMessageId = ActorChannel.getForwardMessageId(reverseMessageId);
		String reverseSender = ActorChannel.getSender(reverseMessageId);
		String forwardSender = ActorChannel.getSender(forwardMessageId);

		if("Y".equals(isReply)){
			ActorChannel.receipt(reverseMessageId);
		}
		ActorSystem.getActor(sender).onReceive(forwardMessageId, reverseMessageId, message, forwardSender, reverseSender);
	}
}
