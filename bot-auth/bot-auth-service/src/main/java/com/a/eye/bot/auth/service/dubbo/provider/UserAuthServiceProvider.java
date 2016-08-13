package com.a.eye.bot.auth.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.auth.service.service.UserAuthService;
import com.a.eye.bot.auth.share.dubbo.provider.IUserAuthServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class UserAuthServiceProvider implements IUserAuthServiceProvider {

	@Autowired
	private UserAuthService service;

	@Override
	public Long authUser(String authCode, String password) {
		return service.authUser(authCode, password);
	}

	@Override
	public Long authUser(String authToken) {
		return service.authUser(authToken);
	}

	@Override
	public boolean checkAuthCode(String authCode) {
		return service.checkAuthCode(authCode);
	}
}
