package com.a.eye.bot.common.util;

import java.util.HashMap;
import java.util.Map;

public class BotIdGenerate {

	private static Map<RegistedBot, Long> botIdMap = new HashMap<RegistedBot, Long>();

	public synchronized static String generate(RegistedBot botName) {
		if (botIdMap.get(botName) == null) {
			botIdMap.put(botName, 0l);
		} else {
			botIdMap.put(botName, botIdMap.get(botName) + 1);
		}
		return botName + String.valueOf(botIdMap.get(botName));
	}
}
