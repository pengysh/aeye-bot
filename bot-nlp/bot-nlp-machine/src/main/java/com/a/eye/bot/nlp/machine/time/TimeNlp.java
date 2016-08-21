package com.a.eye.bot.nlp.machine.time;

import com.a.eye.bot.nlp.machine.time.nlp.TimeNormalizer;

public class TimeNlp {
	private static String path = TimeNormalizer.class.getResource("").getPath();
	private static String classPath = path.substring(0, path.indexOf("/com/ai/bot/nlp/time"));
	private static TimeNormalizer normalizer = new TimeNormalizer(classPath+"/TimeExp.m");
	
	public static TimeNormalizer getTimeNormalizer(){
		return normalizer;
	}
}
