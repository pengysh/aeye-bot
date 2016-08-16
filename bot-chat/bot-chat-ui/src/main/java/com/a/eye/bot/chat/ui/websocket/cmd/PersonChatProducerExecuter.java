package com.a.eye.bot.chat.ui.websocket.cmd;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.share.content.PersonTalkContent;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.chat.share.util.PersonChatGroupIdGen;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component("PersonChatProducer")
public class PersonChatProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(PersonChatProducerExecuter.class.getName());

	private Gson gson = new Gson();

	@Value("${ui_service_topicName}")
	private String topicName;

	private static final String Message_Param = "message";
	private static final String Sender_Param = "sender";
	private static final String Receiver_Param = "receiver";

	public void sendMessage(JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		PersonTalkContent content = new PersonTalkContent();
		content.setSender(contentJson.get(Sender_Param).getAsLong());
		content.setReceiver(contentJson.get(Receiver_Param).getAsLong());
		content.setMessage(contentJson.get(Message_Param).getAsString());
		content.setSendTime(new Date().getTime());

		Long messageId = this.producer(gson.toJson(content), "PersonChat", topicName);

		this.sendToSelf(messageId, content);
	}

	/**
	 * @Title: sendToSelf
	 * @author: pengysh
	 * @date 2016年8月16日 下午1:03:55
	 * @Description:展现自己发送的消息
	 * @param messageId
	 * @param content
	 */
	private void sendToSelf(Long messageId, PersonTalkContent content) {
		String groupId = PersonChatGroupIdGen.gen(content.getSender(), content.getReceiver());

		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.setGroupId(groupId);
		chatMessageInfo.setMessage(content.getMessage());
		chatMessageInfo.setMessageId(messageId);
		chatMessageInfo.setSender(content.getSender());
		chatMessageInfo.setSendTime(content.getSendTime());
		chatMessageInfo.setReceiver(content.getSender());

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
