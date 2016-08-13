package com.a.eye.bot.user.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.user.service.dao.UserFriendsMapper;
import com.a.eye.bot.user.service.entity.UserFriends;
import com.a.eye.bot.user.share.entity.UserInfo;
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
	private UserFriendsMapper userFriendsMapper;

	@Autowired
	private UserService userService;

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
		UserFriends userFriends = userFriendsMapper.selectByPrimaryKey(userId);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			return Constants.EmptyString;
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);

			Long[] userIds = new Long[friendsJson.size()];
			for (int i = 0; i < friendsJson.size(); i++) {
				userIds[i] = friendsJson.get(i).getAsLong();
			}

			List<UserInfo> userInfoList = userService.getBatchUserData(userIds);
			String userFriendsJson = gson.toJson(userInfoList);

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
		UserFriends userFriends = userFriendsMapper.selectByPrimaryKey(userId);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			return null;
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
