package com.a.eye.bot.chat.ui.websocket.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.a.eye.bot.chat.ui.websocket.ChatWebSocketHandler;
import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.util.DateUtil;
import com.google.gson.JsonObject;

/**
 * @Title: ChatMessageRecevieConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月15日 下午1:20:40
 * @Description:聊天消息转发到用户客户端
 */
@Component("ChatMessageRecevieConsumer")
public class ChatMessageRecevieConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(ChatMessageRecevieConsumerExecuter.class.getName());

	private static final String Receiver_Param = "receiver";
	private static final String Sender_Param = "sender";
	private static final String SenderTime_Param = "sendTime";

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到消息：" + messageId + "\t" + contentJson.toString());
		Long sender = contentJson.get(Sender_Param).getAsLong();
		Long sendTime = contentJson.get(SenderTime_Param).getAsLong();
		UserCacheInfo userInfo = userInfoJedisRepository.selectUserInfo(sender);
		contentJson.addProperty("Cmd", "ChatMessage");
		contentJson.addProperty("senderName", userInfo.getName());
		contentJson.addProperty("headImage", userInfo.getHeadImage());
		contentJson.addProperty("sendTimeFormat", DateUtil.parse(sendTime));
		String contentStr = contentJson.toString();
		Long receiver = contentJson.get(Receiver_Param).getAsLong();

		ChatWebSocketHandler.sendMessageToUser(receiver, new TextMessage(contentStr));
	}
}
