package com.a.eye.bot.auth.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.auth.service.service.UserAuthDataService;
import com.a.eye.bot.auth.share.dubbo.provider.IUserAuthDataServiceProvider;
import com.a.eye.bot.common.exception.MessageException;
import com.alibaba.dubbo.config.annotation.Service;

@Service(retries = 0, cluster = "failsafe")
public class UserAuthDataServiceProvider implements IUserAuthDataServiceProvider {

	@Autowired
	private UserAuthDataService service;

	@Override
	public void addUserAuthData(Long userId, String authCode, String password) throws MessageException {
		service.addUserAuthData(userId, authCode, password);
	}

	@Override
	public void deleteUserAuthData(Long userId, String authCode) throws MessageException {
		service.deleteUserAuthData(userId, authCode);
	}

	@Override
	public void modifyUserAuthData(Long userId, String authCode, String password) throws MessageException {
		service.modifyUserAuthData(userId, authCode, password);
	}
}
