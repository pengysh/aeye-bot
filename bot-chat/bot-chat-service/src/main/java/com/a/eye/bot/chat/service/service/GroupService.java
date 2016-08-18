package com.a.eye.bot.chat.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.Group;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @Title: ChatGroupService.java
 * @author: pengysh
 * @date 2016年8月16日 下午12:24:49
 * @Description:聊天群组服务
 */
@Service
public class GroupService {

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	/**
	 * @Title: getGroupUserIds
	 * @author: pengysh
	 * @date 2016年8月16日 下午12:26:46
	 * @Description:获取群组中的成员列表
	 * @param groupId
	 * @return
	 */
	public List<Long> getGroupUserIds(String groupId) {
		Query query = new Query(Criteria.where("_id").is(groupId));
		Group chatGroup = template.findOne(query, Group.class);
		String users = chatGroup.getUsers();
		List<Long> storeUserIds = gson.fromJson(users, new TypeToken<List<Long>>() {
		}.getType());
		return storeUserIds;
	}

	/**
	 * @Title: getGroupInfo
	 * @author: pengysh
	 * @date 2016年8月17日 下午4:45:22
	 * @Description:群组查询
	 * @param groupId
	 * @return
	 */
	public Group getGroupInfo(String groupId) {
		Query query = new Query(Criteria.where("_id").is(groupId));
		Group chatGroup = template.findOne(query, Group.class);
		return chatGroup;
	}
}
