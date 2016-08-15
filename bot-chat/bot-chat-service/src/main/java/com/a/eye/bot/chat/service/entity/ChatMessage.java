package com.a.eye.bot.chat.service.entity;

public class ChatMessage {

	private String messageId;

	private String groupId;

	private Long sender;

	private String message;

	private Long sendTime;

	public ChatMessage(String messageId, String groupId, Long sender, String message, Long sendTime) {
		this.messageId = messageId;
		this.groupId = groupId;
		this.sender = sender;
		this.message = message;
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "ChatMessage [messageId=" + messageId + ", groupId=" + groupId + ", sender=" + sender + ", message=" + message + ", sendTime=" + sendTime + "]";
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
}
