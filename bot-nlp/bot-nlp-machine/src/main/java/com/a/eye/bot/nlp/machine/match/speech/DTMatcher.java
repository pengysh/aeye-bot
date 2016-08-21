package com.a.eye.bot.nlp.machine.match.speech;

import java.util.Map;

import com.a.eye.bot.nlp.machine.match.Word;

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
