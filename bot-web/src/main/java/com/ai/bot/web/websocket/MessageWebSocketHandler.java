package com.ai.bot.web.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ai.bot.web.bridgebot.creator.BridgeBotApi;
import com.ai.bot.web.bridgebot.message.BridgeBotMessage;
import com.ai.bot.web.service.entity.ChatMessage;
import com.ai.bot.web.service.logic.AccountLogic;
import com.ai.bot.web.service.logic.ChatMessageLogic;
import com.ai.bot.web.service.util.AccountRefUtil;
import com.ai.bot.web.service.util.Constants;
import com.ai.bot.web.websocket.cmd.CmdBase;
import com.ai.bot.web.websocket.cmd.SendMessageCmd;
import com.ai.message.actor.ActorRef;
import com.ai.message.util.MessageIdGenerate;
import com.google.gson.Gson;

public class MessageWebSocketHandler implements WebSocketHandler {

	private final Logger logger = LogManager.getLogger(this.getClass());

	private static final Map<String, WebSocketSession> users = new HashMap<String, WebSocketSession>();

	private Gson gson = new Gson();

	@Autowired
	private ChatMessageLogic chatMessageLogic;

	@Autowired
	private AccountLogic accountLogic;

	private ActorRef bridgeActor;

	private BridgeBotApi bridgeBotApi = new BridgeBotApi();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.error("ConnectionEstablished");

		String accountId = session.getAttributes().get(Constants.AccountId).toString();
		logger.debug("accountId:" + accountId);

		users.put(accountId, session);

		bridgeActor = bridgeBotApi.getCache(accountId);
		if (bridgeActor == null) {
			bridgeActor = bridgeBotApi.createBot(accountId);
		}

		// 更新状态为在线
		accountLogic.updateState(accountId, Constants.Online);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String messageStr = message.getPayload().toString();
		logger.debug("message PayloadLength：" + message.getPayloadLength() + ",payload: " + messageStr);

		if (message.getPayloadLength() > 5) {
			CmdBase cmd = gson.fromJson(messageStr, CmdBase.class);

			if (Constants.SendMessageCmd.equals(cmd.getCmd())) {
				SendMessageCmd sendMessageCmd = gson.fromJson(messageStr, SendMessageCmd.class);
				String myAccountId = sendMessageCmd.getMyAccountId();

				String accountRef = AccountRefUtil.getAccountRef(myAccountId, sendMessageCmd.getPersonAccountId());
				ChatMessage chatMessage = new ChatMessage();
				chatMessage.setAccountRef(accountRef);
				chatMessage.setFromAccount(myAccountId);
				chatMessage.setToAccount(sendMessageCmd.getPersonAccountId());
				chatMessage.setMessage(sendMessageCmd.getMessage());
				chatMessage.setSendTime(new Date());
				chatMessage.setSource(sendMessageCmd.getSource());

				String replyMessageStr = "";
				if ("person".equals(chatMessage.getSource())) {
					chatMessageLogic.sendMessage(chatMessage);

					// 发给自己
					replyMessageStr = chatMessageLogic.createReplyDetailMessage(chatMessage);
					session.sendMessage(new TextMessage(replyMessageStr));
				}

				// 发送给对方
				if (Constants.SystemAccountId.equals(sendMessageCmd.getPersonAccountId())) {
					this.sendToBot(session, gson.toJson(chatMessage));
				} else {
					MessageWebSocketHandler.sendMessageToUser(sendMessageCmd.getPersonAccountId(), new TextMessage(replyMessageStr));
				}
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);

		logger.debug("handleTransportError" + exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		users.remove(session);
		logger.debug("afterConnectionClosed" + closeStatus.getReason());

		String accountId = session.getAttributes().get(Constants.AccountId).toString();
		logger.debug("accountId:" + accountId);
		// 更新状态为离线
		accountLogic.updateState(accountId, Constants.Offline);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public static void sendMessageToUser(String accountId, TextMessage message) {
		WebSocketSession session = users.get(accountId);
		if (session != null) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendToBot(WebSocketSession session, String message) throws IOException {
		logger.debug(bridgeActor.path() + "发送消息给会议机器人");
		// String response = nlp.response(message);
		// session.sendMessage(new TextMessage(response));
		String messageId = MessageIdGenerate.generateFirstMessageId();
		bridgeActor.tell(messageId, new BridgeBotMessage(message), ActorRef.noSender());
	}
}
