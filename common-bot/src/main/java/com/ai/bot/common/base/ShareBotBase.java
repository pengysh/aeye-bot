package com.ai.bot.common.base;

import java.util.Date;

public abstract class ShareBotBase extends BotBase {

	public ShareBotBase(String id, String state) {
		super(id, null, null, state);
	}

	public ShareBotBase(String id, Date startTime, Date endTime, String state) {
		super(id, startTime, endTime, state);
	}
}
