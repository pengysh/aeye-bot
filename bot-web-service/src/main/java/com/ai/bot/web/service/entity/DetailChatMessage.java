package com.ai.bot.web.service.entity;

public class DetailChatMessage extends ChatMessage {

	public DetailChatMessage(ChatMessage chatMessage) {
		this.setAccountRef(chatMessage.getAccountRef());
		this.setFromAccount(chatMessage.getFromAccount());
		this.setMessage(chatMessage.getMessage());
		this.setMessageId(chatMessage.getMessageId());
		this.setSendTime(chatMessage.getSendTime());
		this.setToAccount(chatMessage.getToAccount());
	}

	private String fromAccountName;

	private String fromAccountHeadImage;

	public String getFromAccountName() {
		return fromAccountName;
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}

	public String getFromAccountHeadImage() {
		return fromAccountHeadImage;
	}

	public void setFromAccountHeadImage(String fromAccountHeadImage) {
		this.fromAccountHeadImage = fromAccountHeadImage;
	}

}
