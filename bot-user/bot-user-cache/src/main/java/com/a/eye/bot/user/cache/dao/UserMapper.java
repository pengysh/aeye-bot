package com.a.eye.bot.user.cache.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.a.eye.bot.user.cache.entity.User;

public interface UserMapper {

	@Select("select * from user limit #{start}, #{pageSize}")
	@ResultMap("BaseResultMap")
	List<User> selectOnPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
}