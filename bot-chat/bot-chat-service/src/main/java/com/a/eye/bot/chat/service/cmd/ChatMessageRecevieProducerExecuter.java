package com.a.eye.bot.chat.service.cmd;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.share.content.PersonTalkContent;
import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component("ChatMessageRecevieProducer")
public class ChatMessageRecevieProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(ChatMessageRecevieProducerExecuter.class.getName());

	private Gson gson = new Gson();
	
	@Value("${service_ui_topicName}")
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

		this.producer(gson.toJson(content), "ChatMessageRecevie", topicName);
	}
}
