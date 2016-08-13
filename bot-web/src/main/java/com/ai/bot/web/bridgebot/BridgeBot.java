package com.ai.bot.web.bridgebot;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;

import com.ai.bot.common.base.ShareBotBase;
import com.ai.bot.common.base.StateBase;
import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;
import com.ai.bot.common.message.PromptAckMessage;
import com.ai.bot.common.util.SpringContextUtil;
import com.ai.bot.meetingscene.creator.MeetingSceneBotApi;
import com.ai.bot.meetingscene.message.AskMeWithinTimeAckMeetingSceneMessage;
import com.ai.bot.meetingscene.message.AskTimeWithinMeAckMeetingSceneMessage;
import com.ai.bot.web.bridgebot.message.BridgeBotMessage;
import com.ai.bot.web.httpclient.HttpClientUtil;
import com.ai.bot.web.service.entity.ChatMessage;
import com.ai.bot.web.service.logic.ChatMessageLogic;
import com.ai.bot.web.websocket.MessageWebSocketHandler;
import com.ai.message.actor.ActorRef;
import com.google.gson.Gson;

public class BridgeBot extends ShareBotBase {

	private static Logger logger = LogManager.getLogger(BridgeBot.class.getName());

	private ChatMessageLogic chatMessageLogic = SpringContextUtil.getBean(ChatMessageLogic.class);

	private ChatMessage chatMessage;

	private Gson gson = new Gson();

	public BridgeBot(String id) {
		super(id, State.Idle);
	}

	class State extends StateBase {
	}

	@Override
	public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
		logger.debug("机器人收到消息");
		BridgeBotMessage reqMessage = (BridgeBotMessage) message;
		chatMessage = gson.fromJson(reqMessage.getContent(), ChatMessage.class);

		MeetingSceneBotApi api = new MeetingSceneBotApi(getContext());
		ActorRef actorRef = api.createBot(chatMessage.getFromAccount());

		BridgeBotMethod method = new BridgeBotMethod();
		if ("person".equals(chatMessage.getSource())) {
			String sentence = HttpClientUtil.getInstance().sendHttpGet("http://localhost:9999/wordTree?question=" + chatMessage.getMessage());
			method.parsePersonContent(reverseMessageId, getSelf(), actorRef, sentence);
		} else if ("system".equals(chatMessage.getSource())) {
			method.parseSystemContent(reverseMessageId, getSelf(), actorRef, chatMessage.getMessage());
		}
	}

	@Override
	public void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender) {
		logger.debug("桥接机器人收到消息回执");
		String fromAccount = chatMessage.getFromAccount();
		String toAccount = chatMessage.getToAccount();
		chatMessage.setFromAccount(toAccount);
		chatMessage.setToAccount(fromAccount);
		chatMessage.setSendTime(new Date());

		for (int i = 0; i < messageList.size(); i++) {
			BotAckMessage botAckMessage = messageList.get(i);
			if (botAckMessage instanceof AskMeWithinTimeAckMeetingSceneMessage) {
				AskMeWithinTimeAckMeetingSceneMessage content = (AskMeWithinTimeAckMeetingSceneMessage) botAckMessage;

				chatMessage.setMessage(content.getReplyMessage());
				String replyStr = chatMessageLogic.createReplyDetailMessage(chatMessage);
				chatMessageLogic.sendMessage(chatMessage);
				MessageWebSocketHandler.sendMessageToUser(this.getId(), new TextMessage(replyStr));
			} else if (botAckMessage instanceof AskTimeWithinMeAckMeetingSceneMessage) {
				AskTimeWithinMeAckMeetingSceneMessage content = (AskTimeWithinMeAckMeetingSceneMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",id:" + content.getId());

				chatMessage.setMessage(content.getId() + "有空");
				String replyStr = chatMessageLogic.createReplyDetailMessage(chatMessage);
				chatMessageLogic.sendMessage(chatMessage);
				MessageWebSocketHandler.sendMessageToUser(this.getId(), new TextMessage(replyStr));
			} else if (botAckMessage instanceof PromptAckMessage) {
				PromptAckMessage content = (PromptAckMessage) botAckMessage;

				chatMessage.setMessage(content.getPromptContent());
				String replyStr = chatMessageLogic.createReplyDetailMessage(chatMessage);
				chatMessageLogic.sendMessage(chatMessage);
				MessageWebSocketHandler.sendMessageToUser(this.getId(), new TextMessage(replyStr));
			}
		}
	}
}
