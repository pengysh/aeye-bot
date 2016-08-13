package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.ChatMessage;
import com.ai.bot.web.service.entity.ChatMessageExample;
import java.util.List;

public interface ChatMessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    List<ChatMessage> selectByExample(ChatMessageExample example);

    ChatMessage selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);
}