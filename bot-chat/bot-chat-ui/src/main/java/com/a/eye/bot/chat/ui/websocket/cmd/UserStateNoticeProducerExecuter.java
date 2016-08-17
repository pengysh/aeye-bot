package com.a.eye.bot.chat.ui.websocket.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.google.gson.JsonObject;

@Component("UserStateNoticeProducer")
public class UserStateNoticeProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(UserStateNoticeProducerExecuter.class.getName());

	@Value("${ui_service_topicName}")
	private String topicName;

	public static final String State_Param = "state";
	public static final String UserId_Param = "userId";

	@Override
	public void sendMessage(JsonObject contentJson) {
		logger.debug(contentJson.get(UserId_Param).getAsString() + "\t" + contentJson.get(UserId_Param).getAsString());
		this.producer(contentJson.toString(), "UserStateNotice", topicName);
	}
}
