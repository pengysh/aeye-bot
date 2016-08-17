package com.a.eye.bot.chat.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.UserGroup;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @Title: UserChatGroupDataService.java
 * @author: pengysh
 * @date 2016年8月14日 下午7:57:26
 * @Description:用户所在群数据服务
 */
@Service
public class UserGroupDataService {

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
		template.insert(new UserGroup(userId));
	}
	
	/**
	 * @Title: joinGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:02:34
	 * @Description:加入群
	 * @param userId
	 * @param groupId
	 */
	public void joinGroup(Long userId, String groupId, Boolean realGroup) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserGroup userChatGroup = template.findOne(query, UserGroup.class);
		String groups = userChatGroup.getGroups();
		JsonArray groupJson = gson.fromJson(groups, JsonArray.class);
		JsonObject group = new JsonObject();
		group.addProperty("realGroup", realGroup);
		group.addProperty("groupId", groupId);
		groupJson.add(group);
		template.updateFirst(query, Update.update("groups", groupJson.toString()), UserGroup.class);
	}

	/**
	 * @Title: quitGroup
	 * @author: pengysh
	 * @date 2016年8月14日 下午8:16:27
	 * @Description:退群
	 * @param userId
	 * @param groupId
	 */
	public void quitGroup(Long userId, String groupId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserGroup userChatGroup = template.findOne(query, UserGroup.class);
		String groups = userChatGroup.getGroups();
		JsonArray groupJson = gson.fromJson(groups, JsonArray.class);
		for (int i = 0; i < groupJson.size(); i++) {
			JsonObject group = groupJson.get(i).getAsJsonObject();
			if (group.get("groupId").getAsString().equals(groupId)) {
				groupJson.remove(i);
			}
		}

		template.updateFirst(query, Update.update("groups", groupJson.toString()), UserGroup.class);
	}
}
