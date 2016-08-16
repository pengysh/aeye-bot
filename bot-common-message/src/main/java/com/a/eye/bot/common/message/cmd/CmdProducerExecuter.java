package com.a.eye.bot.common.message.cmd;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.a.eye.bot.common.message.actor.ActorProducer;
import com.a.eye.bot.common.message.util.MessageIdGenerate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class CmdProducerExecuter implements CmdExecuter {

	private Logger logger = LogManager.getLogger(CmdProducerExecuter.class.getName());

	private Gson gson = new Gson();

	protected Long producer(String contentJson, String consumerCmd, String topicName) {
		logger.debug(contentJson);
		JsonObject contentJsonObj = gson.fromJson(contentJson, JsonObject.class);

		Cmd cmd = new Cmd();
		cmd.setCmd(consumerCmd);
		cmd.setContent(contentJsonObj);

		Long messageId = MessageIdGenerate.generateFirstMessageId();

		logger.debug("发送消息topicName：" + topicName);
		ActorProducer.getProducer().send(new ProducerRecord<Long, String>(topicName, messageId, gson.toJson(cmd)));

		return messageId;
	}

	public void receiveMessage(Long messageId, JsonObject contentJson) {
	}
}
