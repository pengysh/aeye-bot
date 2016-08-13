package com.ai.bot.web.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.ChatFriendsMapper;
import com.ai.bot.web.service.entity.ChatFriends;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@Service
@Transactional
public class ChatFriendsLogic {

	@Autowired
	private ChatFriendsMapper chatFriendsMapper;

	private Gson gson = new Gson();

	public void create(Long accountId) {
		ChatFriends chatFriends = new ChatFriends();
		chatFriends.setAccountId(accountId);
		chatFriendsMapper.insert(chatFriends);
	}

	public void addFriends(Long accountId, Long[] friendsAccountId) {
		ChatFriends chatFriends = chatFriendsMapper.selectByPrimaryKey(accountId);
		String friendsJsonStr = gson.toJson(friendsAccountId);
		chatFriends.setFriends(friendsJsonStr);
		chatFriendsMapper.updateByPrimaryKey(chatFriends);
	}

	public void removeFriends(Long accountId, Long friendsAccountId) {
		ChatFriends chatFriends = chatFriendsMapper.selectByPrimaryKey(accountId);
		String friendsJsonStr = chatFriends.getFriends();
		JsonArray friendsJson = gson.fromJson(friendsJsonStr, JsonArray.class);
		for (int i = 0; i < friendsJson.size(); i++) {
			Long friend = friendsJson.get(i).getAsLong();
			if (friendsAccountId == friend) {
				friendsJson.remove(i);
				break;
			}
		}
		chatFriends.setFriends(friendsJson.toString());
		chatFriendsMapper.updateByPrimaryKey(chatFriends);
	}
}
