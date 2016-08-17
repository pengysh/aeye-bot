package com.a.eye.bot.chat.ui.websocket.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.a.eye.bot.chat.ui.websocket.ChatWebSocketHandler;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.google.gson.JsonObject;

/**
 * @Title: UserStateNoticeConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月16日 下午6:57:21
 * @Description：用户状态通知
 */
@Component("UserStateNoticeConsumer")
public class UserStateNoticeConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(UserStateNoticeConsumerExecuter.class.getName());

	private static final String Receiver_Param = "receiver";

	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到消息：" + messageId + "\t" + contentJson.toString());
		Long receiver = contentJson.get(Receiver_Param).getAsLong();
		contentJson.addProperty("Cmd", "UserStateNotice");

		ChatWebSocketHandler.sendMessageToUser(receiver, new TextMessage(contentJson.toString()));
	}
}
