package com.a.eye.bot.common.message.cmd;

import com.google.gson.JsonObject;

public class Cmd {

	private String cmd;

	private JsonObject content;

	public String getProducerCmd() {
		return cmd + "Producer";
	}
	
	public String getConsumerCmd() {
		return cmd + "Consumer";
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public JsonObject getContent() {
		return content;
	}

	public void setContent(JsonObject content) {
		this.content = content;
	}
}
