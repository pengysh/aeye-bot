package com.a.eye.bot.chat.share.dubbo.provider;

import com.google.gson.JsonArray;

public interface IGroupDataServiceProvider {
	public void createChatGroup(String title, String purpose, boolean publicOrPrivate, JsonArray userJson);
}
