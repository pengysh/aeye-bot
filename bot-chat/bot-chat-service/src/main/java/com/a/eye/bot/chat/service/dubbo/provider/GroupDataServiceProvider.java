package com.a.eye.bot.chat.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.chat.service.service.GroupDataService;
import com.a.eye.bot.chat.share.dubbo.provider.IGroupDataServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

@Service
public class GroupDataServiceProvider implements IGroupDataServiceProvider {

	@Autowired
	private GroupDataService groupDataService;

	@Override
	public void createChatGroup(String title, String purpose, boolean publicOrPrivate, String userJsonStr) {
		groupDataService.createChatGroup(title, purpose, publicOrPrivate, userJsonStr);
	}
}
