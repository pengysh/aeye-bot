package com.a.eye.bot.chat.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.chat.service.service.UserGroupService;
import com.a.eye.bot.chat.share.dubbo.provider.IUserGroupServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class UserGroupServiceProvider implements IUserGroupServiceProvider {

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public String getUserGroup(Long userId) {
		return userGroupService.getUserGroup(userId);
	}

}
