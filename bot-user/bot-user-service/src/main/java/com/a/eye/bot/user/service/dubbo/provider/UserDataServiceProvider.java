package com.a.eye.bot.user.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.common.exception.MessageException;
import com.a.eye.bot.user.service.service.UserDataService;
import com.a.eye.bot.user.share.dubbo.provider.IUserDataServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service(retries = 0, cluster = "failsafe")
public class UserDataServiceProvider implements IUserDataServiceProvider {

	@Autowired
	private UserDataService service;

	@Override
	public void addUserData(String name, String email, String password) throws MessageException {
		service.addUserData(name, email, password);
	}
}
