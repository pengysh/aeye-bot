package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.BotProperty;
import com.ai.bot.web.service.entity.BotPropertyExample;
import java.util.List;

public interface BotPropertyMapper {
    int deleteByPrimaryKey(Integer propertyId);

    int insert(BotProperty record);

    int insertSelective(BotProperty record);

    List<BotProperty> selectByExample(BotPropertyExample example);

    BotProperty selectByPrimaryKey(Integer propertyId);

    int updateByPrimaryKeySelective(BotProperty record);

    int updateByPrimaryKey(BotProperty record);
}