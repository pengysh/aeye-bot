package com.ai.bot.web.service.view;

import java.util.List;

import com.ai.bot.web.service.entity.DetailChatMessage;

public class ChatMessageView extends ViewBase {

	public ChatMessageView(String contentId, List<DetailChatMessage> messageList) {
		super(contentId);
		this.messageList = messageList;
	}

	private List<DetailChatMessage> messageList;

	public List<DetailChatMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<DetailChatMessage> messageList) {
		this.messageList = messageList;
	}
}
