package com.ai.bot.common.util;

import com.ai.message.actor.ActorSystem;

public class MessageSystemContext {

	private static final ActorSystem system = ActorSystem.create("BotSystem");

	public static ActorSystem getActorSystem() {
		return system;
	}
}
