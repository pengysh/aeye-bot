package com.a.eye.bot.chat.service.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.service.service.ChatMessageService;
import com.a.eye.bot.chat.share.content.PersonTalkContent;
import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.chat.share.util.PersonChatGroupIdGen;
import com.a.eye.bot.common.cache.redis.UserStateJedisRepository;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Title: PersonChatConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月15日 下午12:07:55
 * @Description:单对单聊天的消费服务
 */
@Component("PersonChatConsumer")
public class PersonChatConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(PersonChatConsumerExecuter.class.getName());

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
	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		PersonTalkContent content = gson.fromJson(contentJson.toString(), PersonTalkContent.class);
		String groupId = PersonChatGroupIdGen.gen(content.getSender(), content.getReceiver());
		service.saveMessage(messageId, groupId, content.getSender(), content.getMessage(), content.getSendTime());
		this.sendToReceiver(messageId, groupId, content);
	}

	/**
	 * @Title: sendToReceiver
	 * @author: pengysh
	 * @date 2016年8月15日 下午12:32:41
	 * @Description:发送消息到接收者
	 * @param content
	 */
	private void sendToReceiver(Long messageId, String groupId, PersonTalkContent content) {
		String userState = userStateJedisRepository.selectUserState(content.getReceiver());
		logger.debug("用户：" + content.getReceiver() + "\t 状态：" + userState);

		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.setGroupId(groupId);
		chatMessageInfo.setMessage(content.getMessage());
		chatMessageInfo.setMessageId(messageId);
		chatMessageInfo.setSender(content.getSender());
		chatMessageInfo.setSendTime(content.getSendTime());
		chatMessageInfo.setReceiver(content.getReceiver());

		String recevieContentJsonStr = gson.toJson(chatMessageInfo);
		JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);

		// 用户在线则发送
		if (UserStateConstants.Online_State.equals(userState)) {
			Cmd cmd = new Cmd();
			cmd.setCmd("ChatMessageRecevie");
			cmd.setContent(recevieContentJson);

			logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
			CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
			logger.debug("命令执行实例：" + executer.getClass());
			executer.sendMessage(cmd.getContent());
		}
	}
}
