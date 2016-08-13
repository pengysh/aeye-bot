package com.a.eye.bot.user.share.dubbo.provider;

public interface IUserFriendsDataServiceProvider {

	public void addUserFriends(Long userId);

	public void addFriend(Long userId, Long friendsUserId);

	public void removeFriend(Long userId, Long friendsUserId);
}
