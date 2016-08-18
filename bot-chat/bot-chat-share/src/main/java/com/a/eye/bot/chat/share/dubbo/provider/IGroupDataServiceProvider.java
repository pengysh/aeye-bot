package com.a.eye.bot.chat.share.dubbo.provider;

public interface IGroupDataServiceProvider {
	public void createChatGroup(String title, String purpose, boolean publicOrPrivate, String userJsonStr);
}
