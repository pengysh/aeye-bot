package com.a.eye.bot.common.util;

import com.a.eye.bot.common.consts.Constants;

public class BooleanUtil {

    public static boolean getBoolean(String str) {
        if (Constants.Y.equals(str)) {
            return true;
        } else {
            return false;
        }
    }
}
