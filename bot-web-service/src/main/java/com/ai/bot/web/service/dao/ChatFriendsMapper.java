package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.ChatFriends;
import com.ai.bot.web.service.entity.ChatFriendsExample;
import java.util.List;

public interface ChatFriendsMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(ChatFriends record);

    int insertSelective(ChatFriends record);

    List<ChatFriends> selectByExample(ChatFriendsExample example);

    ChatFriends selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(ChatFriends record);

    int updateByPrimaryKey(ChatFriends record);
}