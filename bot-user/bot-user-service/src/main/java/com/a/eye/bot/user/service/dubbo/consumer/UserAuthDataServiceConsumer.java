package com.a.eye.bot.user.service.dubbo.consumer;

import org.springframework.stereotype.Service;

import com.a.eye.bot.auth.share.dubbo.provider.IUserAuthDataServiceProvider;
import com.a.eye.bot.common.exception.MessageException;
import com.alibaba.dubbo.config.annotation.Reference;

@Service
public class UserAuthDataServiceConsumer {

	@Reference(retries = 0)
	private IUserAuthDataServiceProvider provider;

	public void addUserAuthData(Long userId, String authCode, String password) throws MessageException {
		provider.addUserAuthData(userId, authCode, password);
	}

	public void deleteUserAuthData(Long userId, String authCode) throws MessageException {
		provider.deleteUserAuthData(userId, authCode);
	}

	public void modifyUserAuthData(Long userId, String authCode, String password) throws MessageException {
		provider.modifyUserAuthData(userId, authCode, password);
	}
}
