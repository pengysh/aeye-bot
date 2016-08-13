package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.Calendar;
import com.ai.bot.web.service.entity.CalendarExample;
import java.util.List;

public interface CalendarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Calendar record);

    int insertSelective(Calendar record);

    List<Calendar> selectByExample(CalendarExample example);

    Calendar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Calendar record);

    int updateByPrimaryKey(Calendar record);
}