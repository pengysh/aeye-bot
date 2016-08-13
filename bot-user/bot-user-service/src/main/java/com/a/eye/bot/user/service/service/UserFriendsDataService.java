package com.a.eye.bot.user.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.user.service.dao.UserFriendsMapper;
import com.a.eye.bot.user.service.entity.UserFriends;
import com.a.eye.bot.user.share.dubbo.provider.IUserDataServiceProvider;
import com.alibaba.dubbo.config.annotation.Reference;
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
	private UserFriendsMapper userFriendsMapper;

	@Reference
	private IUserDataServiceProvider userDataServiceProvider;

	/**
	 * @Title: addUserFriends
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:28:21
	 * @Description:创建用户时同时创建一条好友记录
	 * @param userId
	 */
	public void addUserFriends(Long userId) {
		UserFriends userFriends = new UserFriends();
		userFriends.setUserId(userId);
		userFriendsMapper.insert(userFriends);
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
		UserFriends userFriends = userFriendsMapper.selectByPrimaryKey(userId);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			JsonArray friendsJson = new JsonArray();
			friendsJson.add(friendsUserId);
			userFriends.setFriends(friendsJson.toString());
			userFriendsMapper.updateByPrimaryKey(userFriends);
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);
			friendsJson.add(friendsUserId);
			userFriends.setFriends(friendsJson.toString());
			userFriendsMapper.updateByPrimaryKey(userFriends);
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
		UserFriends userFriends = userFriendsMapper.selectByPrimaryKey(userId);
		String friends = userFriends.getFriends();
		if (!StringUtils.isEmpty(friends)) {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);
			for (int i = 0; i < friendsJson.size(); i++) {
				if (friendsUserId == friendsJson.get(i).getAsLong()) {
					friendsJson.remove(i);
				}
			}

			userFriends.setFriends(friendsJson.toString());
			userFriendsMapper.updateByPrimaryKey(userFriends);
		}
	}
}
