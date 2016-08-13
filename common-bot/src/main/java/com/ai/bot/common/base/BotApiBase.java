package com.ai.bot.common.base;

import com.ai.message.actor.ActorContext;

public abstract class BotApiBase {
	private ActorContext context;

	public BotApiBase(ActorContext context) {
		this.context = context;
	}

	public ActorContext getContext() {
		return context;
	}
}
