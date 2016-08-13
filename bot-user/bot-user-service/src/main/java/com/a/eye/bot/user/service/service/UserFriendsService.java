package com.a.eye.bot.user.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.user.service.dao.UserFriendsMapper;
import com.a.eye.bot.user.service.entity.UserFriends;
import com.a.eye.bot.user.share.dubbo.provider.IUserServiceProvider;
import com.alibaba.dubbo.config.annotation.Reference;
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

	private Gson gson = new Gson();

	@Autowired
	private UserFriendsMapper userFriendsMapper;

	@Reference
	private IUserServiceProvider userServiceProvider;

	public String getUserFriends(Long userId) {
		UserFriends userFriends = userFriendsMapper.selectByPrimaryKey(userId);
		String friends = userFriends.getFriends();
		if (StringUtils.isEmpty(friends)) {
			return Constants.EmptyString;
		} else {
			JsonArray friendsJson = gson.fromJson(friends, JsonArray.class);
			String userFriendsJsonStr = userServiceProvider.getBatchUserData(friendsJson.toString());
			return userFriendsJsonStr;
		}
	}
}
