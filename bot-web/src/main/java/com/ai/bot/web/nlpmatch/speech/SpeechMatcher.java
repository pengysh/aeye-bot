package com.ai.bot.web.nlpmatch.speech;

import java.util.List;
import java.util.Map;

import com.ai.bot.web.nlpmatch.Word;
import com.ai.bot.web.nlpmatch.result.SpeechResult;

public abstract class SpeechMatcher {
	public abstract List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId);
}
