package com.a.eye.bot.chat.ui.websocket.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.a.eye.bot.chat.ui.websocket.ChatWebSocketHandler;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.google.gson.JsonObject;

/**
 * @Title: GetChatRecordConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月16日 下午1:41:32
 * @Description:获取的聊天记录发送到客户端
 */
@Component("GetChatRecordConsumer")
public class GetChatRecordConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(GetChatRecordConsumerExecuter.class.getName());

	private static final String UserId_Param = "userId";

	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到消息：" + messageId + "\t" + contentJson.toString());
		Long userId = contentJson.get(UserId_Param).getAsLong();
		contentJson.addProperty("Cmd", "GetChatRecord");

		ChatWebSocketHandler.sendMessageToUser(userId, new TextMessage(contentJson.toString()));
	}
}
