package com.a.eye.bot.user.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.user.service.service.UserFriendsService;
import com.a.eye.bot.user.share.dubbo.provider.IUserFriendsServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class UserFriendsServiceProvider implements IUserFriendsServiceProvider {

	@Autowired
	private UserFriendsService userFriendsService;

	@Override
	public String getUserFriends(Long userId) {
		return userFriendsService.getUserFriends(userId);
	}

}
