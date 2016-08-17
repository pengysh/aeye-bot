package com.a.eye.bot.chat.share.dubbo.provider;

import java.util.Map;

public interface IUserFriendsServiceProvider {

	public String getUserFriends(Long userId);

	public Map<Long, Long> getUserFriendIds(Long userId);
}
