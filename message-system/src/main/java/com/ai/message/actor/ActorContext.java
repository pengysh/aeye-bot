package com.ai.message.actor;

import java.util.HashMap;
import java.util.Map;

public class ActorContext extends ActorRefFactory {

	private Map<String, ActorRef> context = new HashMap<String, ActorRef>();

	private static ActorContext actorContext = new ActorContext();

	public static ActorContext getInstance() {
		return actorContext;
	}

	public void pushActorRef(String address, ActorRef actorRef) {
		context.put(address, actorRef);
		ActorSystem.putSelfActorRef(address, actorRef);
	}

	public ActorRef getActorRef(String address) {
		return context.get(address);
	}
}
