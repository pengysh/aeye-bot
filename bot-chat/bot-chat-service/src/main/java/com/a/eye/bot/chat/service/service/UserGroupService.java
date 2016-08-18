package com.a.eye.bot.chat.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.entity.Group;
import com.a.eye.bot.chat.service.entity.UserGroup;
import com.a.eye.bot.chat.share.entity.UserChatGroupContent;
import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * @Title: UserChatGroupService.java
 * @author: pengysh
 * @date 2016年8月16日 下午5:13:07
 * @Description:用户所在群服务
 */
@Service
public class UserGroupService {

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	/**
	 * @Title: getUserGroup
	 * @author: pengysh
	 * @date 2016年8月16日 下午5:13:39
	 * @Description:用户所在群查询
	 * @param userId
	 * @return
	 */
	public String getUserGroup(Long userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserGroup userChatGroup = template.findOne(query, UserGroup.class);

		List<UserChatGroupContent> groupList = new ArrayList<UserChatGroupContent>();
		if (userChatGroup != null) {
			String groups = userChatGroup.getGroups();
			JsonArray groupJson = gson.fromJson(groups, JsonArray.class);

			for (int i = 0; i < groupJson.size(); i++) {
				JsonObject group = groupJson.get(i).getAsJsonObject();
				String groupId = group.get("groupId").getAsString();
				Group groupInfo = groupService.getGroupInfo(groupId);
				UserChatGroupContent userChatGroupContent = new UserChatGroupContent();
				userChatGroupContent.setGroupId(groupId);
				userChatGroupContent.setGroupTitle(groupInfo.getTitle());
				userChatGroupContent.setMemberCount(groupInfo.getUserCount());

				String users = groupInfo.getUsers();
				List<Long> storeUserIds = gson.fromJson(users, new TypeToken<List<Long>>() {
				}.getType());
				for (Long groupUserId : storeUserIds) {
					UserCacheInfo userCacheInfo = userInfoJedisRepository.selectUserInfo(groupUserId);
					userChatGroupContent.getHeadImages().add(userCacheInfo.getHeadImage());
				}

				groupList.add(userChatGroupContent);
			}
		}

		return gson.toJson(groupList);
	}
}
