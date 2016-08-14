package com.a.eye.bot.chat.ui.websocket.cmd;

import java.util.Date;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.a.eye.bot.common.cmd.CmdExecuter;
import com.a.eye.bot.common.message.actor.ActorProducer;

@Component("personTalk")
public class PersonTalkExecuter implements CmdExecuter {

	private Logger logger = LogManager.getLogger(PersonTalkExecuter.class.getName());

	@Override
	public void exe(Long userId, String content) {
		logger.debug("收到命令：" + userId + "\t" + content);
		ActorProducer.getProducer().send(new ProducerRecord<String, String>("aeye", String.valueOf(new Date().getTime()), content));
	}

}
