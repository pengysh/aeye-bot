package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.ChatMessageGroup;
import com.ai.bot.web.service.entity.ChatMessageGroupExample;
import java.util.List;

public interface ChatMessageGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(ChatMessageGroup record);

    int insertSelective(ChatMessageGroup record);

    List<ChatMessageGroup> selectByExample(ChatMessageGroupExample example);

    ChatMessageGroup selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(ChatMessageGroup record);

    int updateByPrimaryKey(ChatMessageGroup record);
}