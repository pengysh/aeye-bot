package com.ai.bot.web.service.entity;

import java.util.Date;

public class ChatMessage {
	private Long messageId;

	private String accountRef;

	private String fromAccount;

	private Date sendTime;

	private String message;

	private String toAccount;

	private String source;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getAccountRef() {
		return accountRef;
	}

	public void setAccountRef(String accountRef) {
		this.accountRef = accountRef == null ? null : accountRef.trim();
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount == null ? null : fromAccount.trim();
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount == null ? null : toAccount.trim();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}