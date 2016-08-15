package com.a.eye.bot.chat.service.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.service.service.ChatMessageService;
import com.a.eye.bot.chat.service.util.PersonTalkGroupIdGenUtil;
import com.a.eye.bot.chat.share.content.PersonTalkContent;
import com.a.eye.bot.chat.share.content.TalkRecevieContent;
import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.chat.share.redis.UserStateJedisRepository;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Title: PersonTalkConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月15日 下午12:07:55
 * @Description:单对单聊天的消费服务
 */
@Component("PersonTalkConsumer")
public class PersonTalkConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(PersonTalkConsumerExecuter.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private ChatMessageService service;

	@Autowired
	private UserStateJedisRepository userStateJedisRepository;

	/**
	 * @Title: receiveMessage
	 * @author: pengysh
	 * @date 2016年8月15日 下午12:07:47
	 * @Description:消费
	 * @param messageId
	 * @param contentJson
	 */
	public void receiveMessage(String messageId, JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		PersonTalkContent content = gson.fromJson(contentJson.toString(), PersonTalkContent.class);
		String groupId = PersonTalkGroupIdGenUtil.gen(content.getSender(), content.getReceiver());
		service.saveMessage(messageId, groupId, content.getSender(), content.getMessage(), content.getSendTime());
		this.sendToReceiver(content);
	}

	/**
	 * @Title: sendToReceiver
	 * @author: pengysh
	 * @date 2016年8月15日 下午12:32:41
	 * @Description:发送消息到接收者
	 * @param content
	 */
	private void sendToReceiver(PersonTalkContent content) {
		String userState = userStateJedisRepository.selectUserState(content.getReceiver());
		logger.debug("用户：" + content.getReceiver() + "\t 状态：" + userState);
		TalkRecevieContent recevieContent = new TalkRecevieContent();
		recevieContent.setMessage(content.getMessage());
		recevieContent.setReceiver(content.getReceiver());
		recevieContent.setSender(content.getSender());
		recevieContent.setSendTime(content.getSendTime());
		String recevieContentJsonStr = gson.toJson(recevieContent);
		JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);

		// 用户在线则发送
		if (UserStateConstants.Online_State.equals(userState)) {
			Cmd cmd = new Cmd();
			cmd.setCmd("TalkRecevie");
			cmd.setContent(recevieContentJson);

			logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
			CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
			logger.debug("命令执行实例：" + executer.getClass());
			executer.sendMessage(cmd.getContent());
		}
	}
}
