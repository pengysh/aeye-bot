package com.a.eye.bot.chat.ui.websocket.cmd;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.share.content.TopicChatContent;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component("TopicChatProducer")
public class TopicChatProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(TopicChatProducerExecuter.class.getName());

	private Gson gson = new Gson();

	@Value("${ui_service_topicName}")
	private String topicName;

	private static final String Message_Param = "message";
	private static final String Sender_Param = "sender";
	private static final String TopicId_Param = "topicId";

	public void sendMessage(JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		TopicChatContent content = new TopicChatContent();
		content.setSender(contentJson.get(Sender_Param).getAsLong());
		content.setTopicId(contentJson.get(TopicId_Param).getAsString());
		content.setMessage(contentJson.get(Message_Param).getAsString());
		content.setSendTime(new Date().getTime());

		Long messageId = this.producer(gson.toJson(content), "TopicChat", topicName);

		this.sendToSelf(messageId, content);
	}

	/**
	 * @Title: sendToSelf
	 * @author: pengysh
	 * @date 2016年8月18日 下午2:49:56
	 * @Description:展现自己发送的消息
	 * @param messageId
	 * @param content
	 */
	private void sendToSelf(Long messageId, TopicChatContent content) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.setGroupId(content.getTopicId());
		chatMessageInfo.setMessage(content.getMessage());
		chatMessageInfo.setMessageId(messageId);
		chatMessageInfo.setSender(content.getSender());
		chatMessageInfo.setSendTime(content.getSendTime());

		String recevieContentJsonStr = gson.toJson(chatMessageInfo);
		JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);
		recevieContentJson.addProperty("userId", content.getSender());

		Cmd cmd = new Cmd();
		cmd.setCmd("ChatMessageRecevie");
		cmd.setContent(recevieContentJson);

		logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
		CmdExecuter executer = SpringContextUtil.getBean(cmd.getConsumerCmd());
		logger.debug("命令执行实例：" + executer.getClass());
		executer.receiveMessage(messageId, cmd.getContent());
	}
}
