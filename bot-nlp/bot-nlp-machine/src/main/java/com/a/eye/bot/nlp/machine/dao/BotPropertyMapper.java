package com.a.eye.bot.nlp.machine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.a.eye.bot.nlp.machine.entity.BotProperty;

public interface BotPropertyMapper {
	int deleteByPrimaryKey(Integer propertyId);

	int insert(BotProperty record);

	int insertSelective(BotProperty record);

	BotProperty selectByPrimaryKey(Integer propertyId);

	int updateByPrimaryKeySelective(BotProperty record);

	int updateByPrimaryKey(BotProperty record);

	@Select("select * from bot_property where bot_type = #{botType}")
	@ResultMap("BaseResultMap")
	List<BotProperty> selectByBotType(@Param("botType") String botType);
}