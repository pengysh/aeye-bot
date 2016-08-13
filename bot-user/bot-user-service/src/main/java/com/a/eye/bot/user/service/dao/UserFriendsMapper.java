package com.a.eye.bot.user.service.dao;

import com.a.eye.bot.user.service.entity.UserFriends;

public interface UserFriendsMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserFriends record);

    int insertSelective(UserFriends record);

    UserFriends selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserFriends record);

    int updateByPrimaryKey(UserFriends record);
}