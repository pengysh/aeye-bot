package com.a.eye.bot.common.message.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.a.eye.bot.common.message.channel.ActorChannel;

public class ActorSession {

	private Logger logger = LogManager.getLogger(ActorSession.class);

	private Map<String, List<Object>> ackMessageCache = new HashMap<String, List<Object>>();

	private static Map<String, AtomicInteger> ackCallNum = new HashMap<String, AtomicInteger>();

	public void push(String forwardMessageId, Object message) {
		if (ackMessageCache.containsKey(forwardMessageId)) {
			ackMessageCache.get(forwardMessageId).add(message);
		} else {
			List<Object> messageList = new ArrayList<Object>();
			messageList.add(message);
			ackMessageCache.put(forwardMessageId, messageList);
		}
	}

	public List<Object> getAckMessageList(String forwardMessageId) {
		return ackMessageCache.get(forwardMessageId);
	}

	public void remove(String forwardMessageId) {
		ackMessageCache.remove(forwardMessageId);
	}

	public boolean isFinished(String forwardMessageId) {
		if (!ackCallNum.containsKey(forwardMessageId)) {
			ackCallNum.put(forwardMessageId, new AtomicInteger(0));
		}

		int callNum = ActorChannel.getForwardCallNum(forwardMessageId);
		int ackNum = ackCallNum.get(forwardMessageId).incrementAndGet();
		logger.debug(forwardMessageId + ",发送的消息个数：" + callNum + ",收到回复的消息个数：" + ackNum);
		if (callNum == ackNum) {
			return true;
		} else {
			return false;
		}
	}
}
