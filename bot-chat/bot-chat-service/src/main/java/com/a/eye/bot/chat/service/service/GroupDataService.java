package com.a.eye.bot.chat.service.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.Group;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

/**
 * @Title: ChatGroupService.java
 * @author: pengysh
 * @date 2016年8月14日 下午7:43:24
 * @Description:聊天群组数据服务
 */
@Service
public class GroupDataService {

	private static Logger logger = LogManager.getLogger(GroupDataService.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	@Autowired
	private UserGroupDataService userGroupDataService;

	/**
	 * @Title: createChatGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午6:58:19
	 * @Description:创建群组
	 * @param groupId
	 * @param title
	 * @param userIds
	 */
	public void createChatGroup(String title, String purpose, boolean publicOrPrivate, String userJsonStr) {
		JsonArray members = gson.fromJson(userJsonStr, JsonArray.class);
		Integer userCount = members.size();
		Group group = new Group(title, purpose, publicOrPrivate, userJsonStr, userCount);
		template.insert(group);
		logger.debug("创建话题：_id:" + group.get_id());

		for (int i = 0; i < members.size(); i++) {
			Long member = members.get(i).getAsLong();
			userGroupDataService.joinGroup(member, group.get_id());
		}
	}

	/**
	 * @Title: modifyTitle
	 * @author: pengysh
	 * @date 2016年8月14日 下午6:59:35
	 * @Description:变更群组标题
	 * @param groupId
	 * @param title
	 */
	public void modifyTitle(Long groupId, String title) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.updateFirst(query, Update.update("title", title), Group.class);
	}

	/**
	 * @Title: addGroupUser
	 * @author: pengysh
	 * @date 2016年8月14日 下午7:45:35
	 * @Description:添加群成员
	 * @param groupId
	 * @param userIds
	 */
	public void addGroupUser(Long groupId, List<Long> userIds) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		Group chatGroup = template.findOne(query, Group.class);
		String users = chatGroup.getUsers();
		List<Long> storeUserIds = gson.fromJson(users, new TypeToken<List<Long>>() {
		}.getType());
		storeUserIds.addAll(userIds);
		template.updateFirst(query, Update.update("users", storeUserIds.toString()), Group.class);
	}

	/**
	 * @Title: quitGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午7:53:22
	 * @Description:退群
	 * @param groupId
	 * @param userId
	 */
	public void quitGroup(Long groupId, Long userId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		Group chatGroup = template.findOne(query, Group.class);
		String users = chatGroup.getUsers();
		List<Long> storeUserIds = gson.fromJson(users, new TypeToken<List<Long>>() {
		}.getType());
		storeUserIds.remove(userId);
		template.updateFirst(query, Update.update("users", storeUserIds.toString()), Group.class);
	}
}
