package com.ai.bot.web.websocket.cmd;

public class SendMessageCmd extends CmdBase {
	private String personAccountId;
	private String message;
	private String source;

	public String getPersonAccountId() {
		return personAccountId;
	}

	public void setPersonAccountId(String personAccountId) {
		this.personAccountId = personAccountId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
