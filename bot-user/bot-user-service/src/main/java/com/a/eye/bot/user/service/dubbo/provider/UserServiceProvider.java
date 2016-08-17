package com.a.eye.bot.user.service.dubbo.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.user.service.service.UserService;
import com.a.eye.bot.user.share.dubbo.provider.IUserServiceProvider;
import com.a.eye.bot.user.share.entity.UserLoginEntity;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@Service
public class UserServiceProvider implements IUserServiceProvider {

	private Gson gson = new Gson();

	@Autowired
	private UserService service;

	@Override
	public UserLoginEntity userLogin(String authCode, String password) {
		return service.userLogin(authCode, password);
	}

	@Override
	public boolean emailCheck(String email) {
		return service.emailCheck(email);
	}

	@Override
	public String getUserDate(Long userId) {
		UserCacheInfo userCacheInfo = service.getUserDate(userId);
		return gson.toJson(userCacheInfo);
	}

	@Override
	public String getBatchUserData(String userIdJsonArrayStr) {
		JsonArray userIdJsonArray = gson.fromJson(userIdJsonArrayStr, JsonArray.class);

		Long[] userIds = new Long[userIdJsonArray.size()];
		for (int i = 0; i < userIdJsonArray.size(); i++) {
			userIds[i] = userIdJsonArray.get(i).getAsLong();
		}

		List<UserCacheInfo> userInfoList = service.getBatchUserData(userIds);

		return gson.toJson(userInfoList);
	}
}
