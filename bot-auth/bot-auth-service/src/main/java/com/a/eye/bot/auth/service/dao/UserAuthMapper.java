package com.a.eye.bot.auth.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.a.eye.bot.auth.service.entity.UserAuth;

public interface UserAuthMapper {
	int deleteByPrimaryKey(Long id);

	int insert(UserAuth record);

	int insertSelective(UserAuth record);

	UserAuth selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserAuth record);

	int updateByPrimaryKey(UserAuth record);

	@Select("select * from user_auth where auth_code = #{authCode}")
	@ResultMap("BaseResultMap")
	List<UserAuth> selectByAuthCode(String authCode);

	@Select("select * from user_auth where userId = #{userId} and auth_code = #{authCode}")
	@ResultMap("BaseResultMap")
	List<UserAuth> selectByUserIdAndAuthCode(Long userId, String authCode);
}