package com.a.eye.bot.chat.share.entity;

public class ChatMessageInfo {

	private Long messageId;

	private String groupId;

	private Long sender;

	private String senderName;

	private String headImage;

	private String message;

	private Long sendTime;

	private String sendTimeFormat;

	private Long receiver;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
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

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getSendTimeFormat() {
		return sendTimeFormat;
	}

	public void setSendTimeFormat(String sendTimeFormat) {
		this.sendTimeFormat = sendTimeFormat;
	}
}
