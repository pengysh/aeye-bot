package com.a.eye.bot.chat.share.content;

public class GetChatRecordContent {

	private Long userId;
	private String chatAboutId;
	private Long fromSendTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChatAboutId() {
		return chatAboutId;
	}

	public void setChatAboutId(String chatAboutId) {
		this.chatAboutId = chatAboutId;
	}

	public Long getFromSendTime() {
		return fromSendTime;
	}

	public void setFromSendTime(Long fromSendTime) {
		this.fromSendTime = fromSendTime;
	}
}
