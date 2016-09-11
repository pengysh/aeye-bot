package com.a.eye.bot.common.base;

import com.a.eye.bot.common.message.actor.ActorContext;

public abstract class BotApiBase {
	private ActorContext context;

	public BotApiBase(ActorContext context) {
		this.context = context;
	}

	public ActorContext getContext() {
		return context;
	}
}
