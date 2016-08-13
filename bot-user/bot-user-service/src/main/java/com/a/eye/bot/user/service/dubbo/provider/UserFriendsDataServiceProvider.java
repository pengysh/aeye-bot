package com.a.eye.bot.user.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.user.service.service.UserFriendsDataService;
import com.a.eye.bot.user.share.dubbo.provider.IUserFriendsDataServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service(retries = 0, cluster = "failsafe")
public class UserFriendsDataServiceProvider implements IUserFriendsDataServiceProvider {

	@Autowired
	private UserFriendsDataService userFriendsDataService;

	@Override
	public void addUserFriends(Long userId) {
		userFriendsDataService.addUserFriends(userId);
	}

	@Override
	public void addFriend(Long userId, Long friendsUserId) {
		userFriendsDataService.addFriend(userId, friendsUserId);
	}

	@Override
	public void removeFriend(Long userId, Long friendsUserId) {
		userFriendsDataService.removeFriend(userId, friendsUserId);
	}

}
