package com.a.eye.bot.nlp.machine.match.speech;

import java.util.List;
import java.util.Map;

import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;

public abstract class SpeechMatcher {
	public abstract List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId);
}
