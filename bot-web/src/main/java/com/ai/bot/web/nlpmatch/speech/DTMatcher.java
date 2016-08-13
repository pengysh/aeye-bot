package com.ai.bot.web.nlpmatch.speech;

import java.util.Map;

import com.ai.bot.web.nlpmatch.Word;

public class DTMatcher {

	private static final String DT = "DT";

	public Word matcher(Map<Integer, Word> words, int id, int parentId) {
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (DT.equals(entry.getValue().getSpeech()) && (id == entry.getValue().getParentIndex())) {
				entry.getValue().setUsed(true);
				return entry.getValue();
			}
		}
		return null;
	}
}
