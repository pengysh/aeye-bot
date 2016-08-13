package com.a.eye.bot.user.service.dubbo.consumer;

import org.springframework.stereotype.Service;

import com.a.eye.bot.auth.share.dubbo.provider.IUserAuthServiceProvider;
import com.alibaba.dubbo.config.annotation.Reference;

@Service
public class UserAuthServiceConsumer {

	@Reference
	private IUserAuthServiceProvider provider;

	public Long authUser(String authCode, String password) {
		return provider.authUser(authCode, password);
	}

	public Long authUser(String authToken) {
		return provider.authUser(authToken);
	}

	public boolean checkAuthCode(String authCode) {
		return provider.checkAuthCode(authCode);
	}
}
