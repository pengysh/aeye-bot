package com.a.eye.bot.chat.ui.cache;

import com.a.eye.bot.common.message.actor.ActorSystem;

public class ChatSystemContext {

	private static final ActorSystem system = ActorSystem.create("BotSystem");

	public static ActorSystem getActorSystem() {
		return system;
	}
}
