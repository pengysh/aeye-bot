package com.ai.bot.web.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ai.bot.web.service.logic.AccountLogic;
import com.ai.bot.web.service.logic.ChatMessageLogic;
import com.ai.bot.web.service.util.Constants;
import com.ai.bot.web.websocket.cmd.CmdBase;
import com.ai.bot.web.websocket.cmd.InitPersonMessageCmd;
import com.ai.message.actor.ActorSystem;
import com.google.gson.Gson;

public class FunctionWebSocketHandler implements WebSocketHandler {

	private final Logger logger = LogManager.getLogger(this.getClass());

	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

	@Autowired
	private AccountLogic accountLogic;

	@Autowired
	private ChatMessageLogic chatMessageLogic;

	public FunctionWebSocketHandler(ActorSystem system) {
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("ConnectionEstablished");
		users.add(session);
		String accountId = session.getAttributes().get(Constants.AccountId).toString();
		String personContacts = accountLogic.getPersonContacts(accountId);
		logger.debug("personContacts: " + personContacts);
		session.sendMessage(new TextMessage(personContacts));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String messageStr = message.getPayload().toString();
		logger.debug("message PayloadLength：" + message.getPayloadLength() + ",payload: " + messageStr);

		if (message.getPayloadLength() > 5) {
			Gson gson = new Gson();
			CmdBase cmd = gson.fromJson(messageStr, CmdBase.class);

			if (Constants.InitPersonMessageCmd.equals(cmd.getCmd())) {
				InitPersonMessageCmd initPersonMessageCmd = gson.fromJson(messageStr, InitPersonMessageCmd.class);
				String myAccountId = initPersonMessageCmd.getMyAccountId();

				session.getAttributes().put(Constants.TalkToAccountId, initPersonMessageCmd.getPersonAccountId());
				String chatMessage = chatMessageLogic.getPersonMessage(myAccountId, initPersonMessageCmd.getPersonAccountId());
				logger.debug("chatMessage: " + chatMessage);
				session.sendMessage(new TextMessage(chatMessage));
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

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
