package com.ai.bot.web.service.dao;

import com.ai.bot.web.service.entity.TodoItem;
import com.ai.bot.web.service.entity.TodoItemExample;
import java.util.List;

public interface TodoItemMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(TodoItem record);

    int insertSelective(TodoItem record);

    List<TodoItem> selectByExample(TodoItemExample example);

    TodoItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(TodoItem record);

    int updateByPrimaryKey(TodoItem record);
}