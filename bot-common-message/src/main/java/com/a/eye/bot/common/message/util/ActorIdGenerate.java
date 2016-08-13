package com.a.eye.bot.common.message.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ActorIdGenerate {

	private static Map<String, AtomicLong> actorIdMap = new HashMap<String, AtomicLong>();

	public static Long increment(String actorType) {
		if (actorIdMap.containsKey(actorType)) {
			return actorIdMap.get(actorType).incrementAndGet();
		} else {
			actorIdMap.put(actorType, new AtomicLong(1));
			return actorIdMap.get(actorType).longValue();
		}
	}
}
