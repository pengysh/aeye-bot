package com.a.eye.bot.chat.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a.eye.bot.chat.service.dao.IUserStateDao;

@Service
public class UserStateService {

	@Autowired
	private IUserStateDao userStateDao;

	public void saveUserState(Long userId, String state) {
		userStateDao.saveUserState(userId, state);
	}
}
