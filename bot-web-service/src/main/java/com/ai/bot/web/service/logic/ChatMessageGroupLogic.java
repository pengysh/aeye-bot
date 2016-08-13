package com.ai.bot.web.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.ChatMessageGroupMapper;
import com.ai.bot.web.service.entity.ChatMessageGroup;

@Service
@Transactional
public class ChatMessageGroupLogic {

	@Autowired
	private ChatMessageGroupMapper chatMessageGroupMapper;

	public void createGroup() {
		ChatMessageGroup group = new ChatMessageGroup();
		chatMessageGroupMapper.insert(group);
	}

	public void addAccount(Integer[] accountId) {
		
	}

	public void setGroupTitle(Integer groupId, String title) {

	}
}
