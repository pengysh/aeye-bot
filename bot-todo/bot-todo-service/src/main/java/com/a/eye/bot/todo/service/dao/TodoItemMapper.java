package com.a.eye.bot.todo.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.a.eye.bot.todo.service.entity.TodoItem;

public interface TodoItemMapper {
	int deleteByPrimaryKey(Long id);

	int insert(TodoItem record);

	int insertSelective(TodoItem record);

	TodoItem selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TodoItem record);

	int updateByPrimaryKey(TodoItem record);

	@Select("select * from todo_item where user_id = #{userId} and start_time >= #{startTime} and end_time <= #{endTime} and state = #{state}")
	@ResultMap("BaseResultMap")
	List<TodoItem> selectUserTodoItem(@Param("userId")Long userId, @Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("state")String state);
}