package com.ai.bot.common.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;

public class BotChatChannel {

	private static Logger logger = LogManager.getLogger(BotChatChannel.class.getName());

	private Map<String, BotReqMessage> requestMessage = new HashMap<String, BotReqMessage>();

	private Map<String, List<BotAckMessage>> ackMessage = new HashMap<String, List<BotAckMessage>>();

	public void addReqMessage(String reqMessageId, BotReqMessage reqMessage) {
		requestMessage.put(reqMessageId, reqMessage);
	}

	public void removeReqMessage(String messageId) {
		requestMessage.remove(messageId);
		logger.debug(messageId + "消息消费完毕，还剩下：" + requestMessage.size() + "条消息");
	}

	public void removeAckMessage(String reqMessageId) {
		ackMessage.remove(reqMessageId);
	}

	public void addAckMessage(String reqMessageId, BotAckMessage botAckMessage) {
		if (ackMessage.get(reqMessageId) == null) {
			List<BotAckMessage> botAckMessageList = new ArrayList<BotAckMessage>();
			botAckMessageList.add(botAckMessage);
			ackMessage.put(reqMessageId, botAckMessageList);
		} else {
			ackMessage.get(reqMessageId).add(botAckMessage);
		}
	}
	
	public List<BotAckMessage> getBotAckMessageList(String reqMessageId){
		return ackMessage.get(reqMessageId);
	}

	public boolean hasFinished() {
		logger.debug("requestMessage size:" + requestMessage.size());
		if (requestMessage.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	class Channel{
	}
}
