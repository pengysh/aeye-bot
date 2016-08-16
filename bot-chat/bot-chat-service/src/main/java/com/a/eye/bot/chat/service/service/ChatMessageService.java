package com.a.eye.bot.chat.service.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.ChatMessage;

/**
 * @Title: ChatMessageService.java
 * @author: pengysh
 * @date 2016年8月14日 下午8:35:05
 * @Description:消息服务
 */
@Service
public class ChatMessageService {
	
	private Logger logger = LogManager.getLogger(ChatMessageService.class.getName());

	@Autowired
	private MongoTemplate template;

	/**
	 * @Title: saveMessage
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:38:08
	 * @Description:保存消息
	 * @param messageId
	 * @param groupId
	 * @param sender
	 * @param message
	 * @param sendTime
	 */
	public void saveMessage(Long messageId, String groupId, Long sender, String message, Long sendTime) {
		template.insert(new ChatMessage(messageId, groupId, sender, message, sendTime));
	}

	/**
	 * @Title: getMessage
	 * @author: pengysh
	 * @date 2016年8月14日 下午9:20:17
	 * @Description:获取消息
	 * @param userId
	 * @return
	 */
	public List<ChatMessage> getMessage(String groupId, Long fromSendTime) {
		logger.debug("获取消息：" + groupId + "\t" + fromSendTime);
		Query query = new Query(new Criteria().andOperator(Criteria.where("groupId").is(groupId), Criteria.where("sendTime").lt(fromSendTime)));

		Integer pageSize = 6;
		Integer pageNow = 1;
		int offset = (pageNow - 1) * pageSize;
		query.limit(pageSize);
		query.skip(offset);
		query.with(new Sort(Direction.DESC, "sendTime"));
		List<ChatMessage> messageList = template.find(query, ChatMessage.class);
		return messageList;
	}
}
