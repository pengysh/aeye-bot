package com.a.eye.bot.chat.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.chat.service.entity.UserFriends;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * @Title: UserFriendsDataService.java
 * @author: pengysh
 * @date 2016年8月13日 下午3:27:54
 * @Description:用户好友数据服务
 */
@Service
@Transactional
public class UserFriendsDataService {

	private Gson gson = new Gson();

	@Autowired
	private MongoTemplate template;

	/**
	 * @Title: addUserFriends
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:28:21
	 * @Description:创建用户时同时创建一条好友记录
	 * @param userId
	 */
	public void addUserFriends(Long userId) {
		template.insert(new UserFriends(userId));
	}

	/**
	 * @Title: addFriend
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:37:23
	 * @Description:添加好友，双方需要互加
	 * @param userId
	 * @param friendsUserId
	 */
	public void addFriend(Long userId, Long friendsUserId) {
		this.addFriendForOneSide(userId, friendsUserId);
		this.addFriendForOneSide(friendsUserId, userId);
	}

	/**
	 * @Title: addFriendForOneSide
	 * @author: pengysh
	 * @date 2016年8月13日 下午7:02:51
	 * @Description:添加好友
	 * @param userId
	 * @param friendsUserId
	 */
	private void addFriendForOneSide(Long userId, Long friendsUserId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserFriends userFriends = template.findOne(query, UserFriends.class);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			JsonArray friendsJson = new JsonArray();
			friendsJson.add(friendsUserId);
			template.updateFirst(query, Update.update("friends", friends), UserFriends.class);
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);
			friendsJson.add(friendsUserId);
			template.updateFirst(query, Update.update("friends", friends), UserFriends.class);
		}
	}

	/**
	 * @Title: removeFriend
	 * @author: pengysh
	 * @date 2016年8月13日 下午7:04:15
	 * @Description:删除好友，双方需要互删
	 * @param userId
	 * @param friendsUserId
	 */
	public void removeFriend(Long userId, Long friendsUserId) {
		this.removeFriendForOneSide(userId, friendsUserId);
		this.removeFriendForOneSide(friendsUserId, userId);
	}

	/**
	 * @Title: removeFriend
	 * @author: pengysh
	 * @date 2016年8月13日 下午6:28:03
	 * @Description:删除好友
	 * @param userId
	 * @param friendsUserId
	 */
	private void removeFriendForOneSide(Long userId, Long friendsUserId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserFriends userFriends = template.findOne(query, UserFriends.class);
		String friends = userFriends.getFriends();
		if (!StringUtils.isEmpty(friends)) {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);
			for (int i = 0; i < friendsJson.size(); i++) {
				if (friendsUserId == friendsJson.get(i).getAsLong()) {
					friendsJson.remove(i);
				}
			}
			template.updateFirst(query, Update.update("friends", friends), UserFriends.class);
		}
	}
}
