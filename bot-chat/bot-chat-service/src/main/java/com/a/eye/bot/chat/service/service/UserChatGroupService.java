package com.a.eye.bot.chat.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.UserChatGroup;
import com.a.eye.bot.chat.service.entity.UserChatGroupContent;

/**
 * @Title: UserChatGroupService.java
 * @author: pengysh
 * @date 2016年8月16日 下午5:13:07
 * @Description:用户所在群服务
 */
@Service
public class UserChatGroupService {

	@Autowired
	private MongoTemplate template;

	/**
	 * @Title: getUserGroup
	 * @author: pengysh
	 * @date 2016年8月16日 下午5:13:39
	 * @Description:用户所在群查询
	 * @param userId
	 * @return
	 */
	public List<UserChatGroupContent> getUserGroup(Long userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserChatGroup userChatGroup = template.findOne(query, UserChatGroup.class);
		List<UserChatGroupContent> groupList = userChatGroup.getGroup();
		return groupList;
	}
}
