package com.a.eye.bot.chat.service.cmd;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.service.service.ChatMessageService;
import com.a.eye.bot.chat.service.service.GroupService;
import com.a.eye.bot.chat.share.content.TopicChatContent;
import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.common.cache.redis.UserStateJedisRepository;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Title: TopicChatConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月18日 下午2:46:24
 * @Description:话题讨论
 */
@Component("TopicChatConsumer")
public class TopicChatConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(TopicChatConsumerExecuter.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private ChatMessageService service;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserStateJedisRepository userStateJedisRepository;

	/**
	 * @Title: receiveMessage
	 * @author: pengysh
	 * @date 2016年8月18日 下午2:46:53
	 * @Description: 消费
	 * @param messageId
	 * @param contentJson
	 */
	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		TopicChatContent content = gson.fromJson(contentJson.toString(), TopicChatContent.class);
		String groupId = content.getTopicId();
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
	private void sendToReceiver(Long messageId, String groupId, TopicChatContent content) {
		List<Long> memberList = groupService.getGroupUserIds(groupId);
		for (Long member : memberList) {
			String userState = userStateJedisRepository.selectUserState(member);
			logger.debug("用户：" + member + "\t 状态：" + userState);

			ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
			chatMessageInfo.setGroupId(groupId);
			chatMessageInfo.setMessage(content.getMessage());
			chatMessageInfo.setMessageId(messageId);
			chatMessageInfo.setSender(content.getSender());
			chatMessageInfo.setSendTime(content.getSendTime());
			chatMessageInfo.setReceiver(member);

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
}
