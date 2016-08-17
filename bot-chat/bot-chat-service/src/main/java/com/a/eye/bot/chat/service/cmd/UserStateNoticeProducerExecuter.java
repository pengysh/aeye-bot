package com.a.eye.bot.chat.service.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.google.gson.JsonObject;

@Component("UserStateNoticeProducer")
public class UserStateNoticeProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(UserStateNoticeProducerExecuter.class.getName());

	@Value("${service_ui_topicName}")
	private String topicName;

	public void sendMessage(JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		this.producer(contentJson.toString(), "UserStateNotice", topicName);
	}
}
