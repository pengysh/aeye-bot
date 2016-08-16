package com.a.eye.bot.common.message.cmd;

import com.google.gson.JsonObject;

public interface CmdExecuter {

	abstract void sendMessage(JsonObject contentJson);

	abstract void receiveMessage(Long messageId, JsonObject contentJson);
}
