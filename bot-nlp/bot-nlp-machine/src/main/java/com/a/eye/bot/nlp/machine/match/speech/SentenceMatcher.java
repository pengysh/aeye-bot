package com.a.eye.bot.nlp.machine.match.speech;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;

public class SentenceMatcher {

	Class<?>[] matchers = { VVMatcher.class, PNMatcher.class, VEMatcher.class, MMatcher.class, NTMatcher.class, NNMatcher.class };

	public List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId) {
		List<SpeechResult> resultAllList = new ArrayList<SpeechResult>();
		for (Class<?> matcher : matchers) {
			// System.out.println(matcher.getName());
			SpeechMatcher speechMatcher = null;
			try {
				speechMatcher = (SpeechMatcher) matcher.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			List<SpeechResult> resultList = speechMatcher.matcher(words, id, parentId);
			resultAllList.addAll(resultList);
		}
		return resultAllList;
	}
}
