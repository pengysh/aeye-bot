package com.a.eye.bot.nlp.machine.time;

import com.a.eye.bot.nlp.machine.time.nlp.TimeNormalizer;

public class TimeNlp {
    private static TimeNormalizer normalizer = new TimeNormalizer("/TimeExp.m");

    public static TimeNormalizer getTimeNormalizer() {
        return normalizer;
    }
}
