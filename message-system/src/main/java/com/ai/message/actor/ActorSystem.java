package com.ai.message.actor;

import java.util.HashMap;
import java.util.Map;

public class ActorSystem extends ActorRefFactory {

	private static ActorSystem system = new ActorSystem();

	private static Map<String, Actor> actorContext = new HashMap<String, Actor>();

	private static ActorProducer producer;

	private static ActorConsumer consumer;

	public static ActorSystem create(String name) {
		producer = new ActorProducer();
		producer.start();

		consumer = new ActorConsumer();
		consumer.start();
		return system;
	}

	public static ActorSystem getContext() {
		return system;
	}

	protected static void pushActor(String path, Actor actor) {
		actorContext.put(path, actor);
	}

	protected static void putSelfActorRef(String path, ActorRef actorRef) {
		actorContext.get(path).setSelf(actorRef);
	}

	public static Actor getActor(String path) {
		return actorContext.get(path);
	}
}
