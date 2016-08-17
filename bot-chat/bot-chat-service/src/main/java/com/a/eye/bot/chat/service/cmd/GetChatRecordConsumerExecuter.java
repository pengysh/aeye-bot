package com.a.eye.bot.chat.service.cmd;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.service.entity.ChatMessage;
import com.a.eye.bot.chat.service.service.ChatMessageService;
import com.a.eye.bot.chat.share.content.GetChatRecordContent;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.DateUtil;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @Title: GetChatRecordConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月16日 上午10:06:33
 * @Description:获取聊天记录
 */
@Component("GetChatRecordConsumer")
public class GetChatRecordConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(GetChatRecordConsumerExecuter.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	@Autowired
	private ChatMessageService service;

	/**
	 * @Title: receiveMessage
	 * @author: pengysh
	 * @date 2016年8月16日 上午10:23:49
	 * @Description:消费
	 * @param messageId
	 * @param contentJson
	 */
	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		GetChatRecordContent content = gson.fromJson(contentJson.toString(), GetChatRecordContent.class);
		List<ChatMessage> messageList = service.getMessage(content.getChatAboutId(), content.getFromSendTime());
		this.sendToGeter(content.getUserId(), messageList, content.getNewOrHis());
	}

	/**
	 * @Title: sendToGeter
	 * @author: pengysh
	 * @date 2016年8月16日 上午10:23:38
	 * @Description: 返回查询结果到查询者
	 * @param userId
	 * @param messageList
	 */
	private void sendToGeter(Long userId, List<ChatMessage> messageList, String newOrHis) {
		List<ChatMessageInfo> contentList = new ArrayList<ChatMessageInfo>();
		for (ChatMessage chatMessage : messageList) {
			ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
			chatMessageInfo.setGroupId(chatMessage.getGroupId());
			chatMessageInfo.setMessage(chatMessage.getMessage());
			chatMessageInfo.setMessageId(chatMessage.getMessageId());
			chatMessageInfo.setSender(chatMessage.getSender());
			chatMessageInfo.setSendTime(chatMessage.getSendTime());

			UserCacheInfo userInfo = userInfoJedisRepository.selectUserInfo(chatMessage.getSender());

			chatMessageInfo.setSenderName(userInfo.getName());
			chatMessageInfo.setHeadImage(userInfo.getHeadImage());
			chatMessageInfo.setSendTimeFormat(DateUtil.parse(chatMessage.getSendTime()));
			contentList.add(chatMessageInfo);
		}

		String messageListJsonStr = gson.toJson(contentList);
		JsonArray messageListJson = gson.fromJson(messageListJsonStr, JsonArray.class);
		JsonObject messageContentJson = new JsonObject();
		messageContentJson.add("messageList", messageListJson);
		messageContentJson.addProperty("userId", userId);
		messageContentJson.addProperty("newOrHis", newOrHis);

		Cmd cmd = new Cmd();
		cmd.setCmd("GetChatRecord");
		cmd.setContent(messageContentJson);

		logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
		CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
		logger.debug("命令执行实例：" + executer.getClass());
		executer.sendMessage(cmd.getContent());
	}
}
