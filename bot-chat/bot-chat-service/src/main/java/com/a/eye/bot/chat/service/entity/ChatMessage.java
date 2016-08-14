package com.a.eye.bot.chat.service.entity;

public class ChatMessage {

	private Long messageId;

	private Long groupId;

	private Long sender;

	private String message;

	private Long sendTime;

	public ChatMessage(Long messageId, Long groupId, Long sender, String message, Long sendTime) {
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

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
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
