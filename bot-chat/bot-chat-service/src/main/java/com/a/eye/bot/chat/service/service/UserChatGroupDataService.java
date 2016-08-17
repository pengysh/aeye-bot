package com.a.eye.bot.chat.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.ChatGroup;
import com.a.eye.bot.chat.service.entity.UserChatGroup;
import com.a.eye.bot.chat.service.entity.UserChatGroupContent;
import com.google.gson.Gson;

/**
 * @Title: UserChatGroupDataService.java
 * @author: pengysh
 * @date 2016年8月14日 下午7:57:26
 * @Description:用户所在群数据服务
 */
@Service
public class UserChatGroupDataService {

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	/**
	 * @Title: createUserChatGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:01:10
	 * @Description:用户注册时同时创建用户群
	 * @param userId
	 */
	public void createUserChatGroup(Long userId) {
		template.insert(new UserChatGroup(userId));
	}

	/**
	 * @Title: joinGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:02:34
	 * @Description:加入群
	 * @param userId
	 * @param groupId
	 */
	public void joinGroup(Long userId, Long groupId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserChatGroup userChatGroup = template.findOne(query, UserChatGroup.class);
		List<UserChatGroupContent> groupList = userChatGroup.getGroup();

		UserChatGroupContent group = new UserChatGroupContent();
		group.setGroupId(groupId);
		group.setOffset(0l);
		groupList.add(group);
		template.updateFirst(query, Update.update("users", gson.toJson(groupList)), ChatGroup.class);
	}

	/**
	 * @Title: quitGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:16:27
	 * @Description:退群
	 * @param userId
	 * @param groupId
	 */
	public void quitGroup(Long userId, Long groupId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserChatGroup userChatGroup = template.findOne(query, UserChatGroup.class);
		List<UserChatGroupContent> groupList = userChatGroup.getGroup();
		for (UserChatGroupContent content : groupList) {
			if (content.getGroupId() == groupId) {
				groupList.remove(content);
				break;
			}
		}

		template.updateFirst(query, Update.update("users", gson.toJson(groupList)), ChatGroup.class);
	}
}
