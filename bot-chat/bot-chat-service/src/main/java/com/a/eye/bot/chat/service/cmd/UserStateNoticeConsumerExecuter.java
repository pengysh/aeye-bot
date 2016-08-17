package com.a.eye.bot.chat.service.cmd;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.service.service.UserFriendsService;
import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.common.cache.redis.UserStateJedisRepository;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.JsonObject;

/**
 * @Title: UserStateNoticeConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月16日 下午5:09:37
 * @Description:用户状态通知
 */
@Component("UserStateNoticeConsumer")
public class UserStateNoticeConsumerExecuter extends CmdConsumerExecuter {

	private Logger logger = LogManager.getLogger(UserStateNoticeConsumerExecuter.class.getName());

	@Autowired
	private UserFriendsService userFriendsService;

	@Autowired
	private UserStateJedisRepository userStateJedisRepository;

	/**
	 * @Title: receiveMessage
	 * @author: pengysh
	 * @date 2016年8月16日 下午5:11:07
	 * @Description:消费
	 * @param messageId
	 * @param contentJson
	 */
	public void receiveMessage(Long messageId, JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		Long userId = contentJson.get("userId").getAsLong();
		String state = contentJson.get("state").getAsString();
		Map<Long, Long> friendsMap = userFriendsService.getUserFriendIds(userId);
		for (Map.Entry<Long, Long> entry : friendsMap.entrySet()) {
			this.sendToReceiver(messageId, userId, entry.getKey(), state);
		}
	}

	/**
	 * @Title: sendToReceiver
	 * @author: pengysh
	 * @date 2016年8月15日 下午12:32:41
	 * @Description:发送消息到接收者
	 * @param content
	 */
	private void sendToReceiver(Long messageId, Long userId, Long receiver, String state) {
		String userState = userStateJedisRepository.selectUserState(userId);
		logger.debug("用户：" + userId + "\t 状态：" + userState);
		JsonObject content = new JsonObject();
		content.addProperty("state", state);
		content.addProperty("userId", userId);
		content.addProperty("receiver", receiver);

		// 用户在线则发送
		if (UserStateConstants.Online_State.equals(userState)) {
			Cmd cmd = new Cmd();
			cmd.setCmd("UserStateNotice");
			cmd.setContent(content);

			logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
			CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
			logger.debug("命令执行实例：" + executer.getClass());
			executer.sendMessage(cmd.getContent());
		}
	}
}
