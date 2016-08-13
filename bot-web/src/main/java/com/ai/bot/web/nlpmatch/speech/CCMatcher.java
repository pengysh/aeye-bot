package com.ai.bot.web.nlpmatch.speech;

import java.util.List;
import java.util.Map;

import com.ai.bot.web.nlpmatch.Word;

public class CCMatcher {

	private static final String CC = "CC";

	public Word matcher(Map<Integer, Word> words, List<Word> wordList) {
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (CC.equals(entry.getValue().getSpeech())) {
				for (Word word1 : wordList) {
					if ((entry.getValue().getIndex() - 1) == word1.getIndex()) {
						for (Word word2 : wordList) {
							if ((entry.getValue().getIndex() + 1) == word2.getIndex()) {
								entry.getValue().setUsed(true);
								return entry.getValue();
							}
						}
					}
				}
			}
		}
		return null;
	}
}
