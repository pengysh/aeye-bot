package com.ai.message.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActorChannel {

	private static Logger logger = LogManager.getLogger(ActorChannel.class);

	private static Map<String, List<String>> forwardCallCache = new HashMap<String, List<String>>();

	private static Map<String, Content> reverseCallCache = new HashMap<String, Content>();

	private static Map<String, AtomicInteger> forwardCallNum = new HashMap<String, AtomicInteger>();

	public static void pushCallRelation(String forwardMessageId, String reverseMessageId, String forwardSender) {
		reverseCallCache.put(reverseMessageId, new Content(forwardMessageId, forwardSender));
		if (forwardCallCache.containsKey(forwardMessageId)) {
			forwardCallCache.get(forwardMessageId).add(reverseMessageId);
		} else {
			List<String> calledList = new ArrayList<String>();
			calledList.add(reverseMessageId);
			forwardCallCache.put(forwardMessageId, calledList);
		}

		if (forwardCallNum.containsKey(forwardMessageId)) {
			forwardCallNum.get(forwardMessageId).incrementAndGet();
		} else {
			forwardCallNum.put(forwardMessageId, new AtomicInteger(1));
		}
	}

	public static String getSender(String reverseMessageId) {
		logger.debug("reverseMessageId：" + reverseMessageId);
		if (reverseCallCache.containsKey(reverseMessageId)) {
			return reverseCallCache.get(reverseMessageId).getSender();
		} else {
			return null;
		}
	}

	public static int getForwardCallNum(String forwardMessageId) {
		return forwardCallNum.get(forwardMessageId).intValue();
	}

	public static String getForwardMessageId(String reverseMessageId) {
		logger.debug("获取消息的上级消息：" + reverseMessageId);
		return reverseCallCache.get(reverseMessageId).getForwardMessageId();
	}

	public static void receipt(String messageId) {
		logger.debug("消息应答：" + messageId);
		 reverseCallCache.remove(messageId);
	}

	static class Content {
		private String forwardMessageId;
		private String sender;

		public Content(String forwardMessageId, String sender) {
			this.forwardMessageId = forwardMessageId;
			this.sender = sender;
		}

		public String getForwardMessageId() {
			return forwardMessageId;
		}

		public String getSender() {
			return sender;
		}
	}
}
