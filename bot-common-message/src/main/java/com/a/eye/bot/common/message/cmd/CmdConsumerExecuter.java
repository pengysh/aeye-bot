package com.a.eye.bot.common.message.cmd;

import com.google.gson.JsonObject;

public abstract class CmdConsumerExecuter implements CmdExecuter {

	protected void consumer(String contentJson, String consumerCmd) {
	}

	public void sendMessage(JsonObject contentJson) {
	}
}
