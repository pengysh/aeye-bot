package com.ai.bot.web.websocket.cmd;

public class InitPersonMessageCmd extends CmdBase {

	private String personAccountId;

	public String getPersonAccountId() {
		return personAccountId;
	}

	public void setPersonAccountId(String personAccountId) {
		this.personAccountId = personAccountId;
	}
}
