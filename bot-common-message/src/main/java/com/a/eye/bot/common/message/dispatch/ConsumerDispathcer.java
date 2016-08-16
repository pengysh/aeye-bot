package com.a.eye.bot.common.message.dispatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;

public class ConsumerDispathcer {

	private Logger logger = LogManager.getLogger(ConsumerDispathcer.class.getName());

	private Gson gson = new Gson();

	public void dispatch(Long messageId, String contentStr) {
		logger.debug("接收到的消息：" + messageId + "\t" + contentStr);
		Cmd cmd = gson.fromJson(contentStr, Cmd.class);
		CmdExecuter executer = SpringContextUtil.getBean(cmd.getConsumerCmd());
		executer.receiveMessage(messageId, cmd.getContent());
	}
}
