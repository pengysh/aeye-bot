package com.a.eye.bot.chat.service.service;

import java.util.List;

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
	public void saveMessage(Long messageId, Long groupId, Long sender, String message, Long sendTime) {
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
	public List<ChatMessage> getMessage(Long userId) {
		Query query = new Query(Criteria.where("userId").is(userId));

		Integer pageSize = 50;
		Integer pageNow = 1;
		int offset = (pageNow - 1) * pageSize;
		query.limit(50);
		query.skip(offset);
		query.with(new Sort(Direction.ASC, "sendTime"));
		List<ChatMessage> messageList = template.find(query, ChatMessage.class);
		return messageList;
	}
}
