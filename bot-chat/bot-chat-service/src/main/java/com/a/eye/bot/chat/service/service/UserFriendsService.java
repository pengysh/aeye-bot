package com.a.eye.bot.chat.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.chat.service.entity.UserFriends;
import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * @Title: UserFriendsService.java
 * @author: pengysh
 * @date 2016年8月13日 下午6:21:34
 * @Description:用户好友服务
 */
@Service
@Transactional
public class UserFriendsService {

	private static Logger logger = LogManager.getLogger(UserFriendsService.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	@Autowired
	private UserFriendsDataService userFriendsDataService;

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	/**
	 * @Title: getUserFriends
	 * @author: pengysh
	 * @date 2016年8月13日 下午10:07:40
	 * @Description:用户好友查询
	 * @param userId
	 * @return
	 */
	public String getUserFriends(Long userId) {
		logger.debug("用户好友查询：" + userId);
		Query query = new Query(Criteria.where("userId").is(userId));
		UserFriends userFriends = template.findOne(query, UserFriends.class);

		//TODO 后期删除
		if (userFriends == null) {
			userFriendsDataService.addUserFriends(userId);
			userFriends = template.findOne(query, UserFriends.class);
		}

		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			return new JsonArray().toString();
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);

			List<UserCacheInfo> userList = new ArrayList<UserCacheInfo>();
			for (int i = 0; i < friendsJson.size(); i++) {
				UserCacheInfo userCacheInfo = userInfoJedisRepository.selectUserInfo(friendsJson.get(i).getAsLong());
				userList.add(userCacheInfo);
			}

			String userFriendsJson = gson.toJson(userList);

			logger.debug("用户好友查询结果：" + userFriendsJson);
			return userFriendsJson;
		}
	}

	/**
	 * @Title: getUserFriendIds
	 * @author: pengysh
	 * @date 2016年8月14日 上午1:12:52
	 * @Description:获取好友ID
	 * @param userId
	 * @return
	 */
	public Map<Long, Long> getUserFriendIds(Long userId) {
		logger.debug("用户好友查询：" + userId);
		Query query = new Query(Criteria.where("userId").is(userId));
		UserFriends userFriends = template.findOne(query, UserFriends.class);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			return new HashMap<Long, Long>();
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);

			Map<Long, Long> userIdMap = new HashMap<Long, Long>();
			for (int i = 0; i < friendsJson.size(); i++) {
				userIdMap.put(friendsJson.get(i).getAsLong(), friendsJson.get(i).getAsLong());
			}
			return userIdMap;
		}
	}
}
