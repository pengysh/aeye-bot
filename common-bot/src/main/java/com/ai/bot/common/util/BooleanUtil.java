package com.ai.bot.common.util;

public class BooleanUtil {

	public static boolean getBoolean(String str) {
		if (Constants.Y.equals(str)) {
			return true;
		} else {
			return false;
		}
	}
}
