package com.ai.bot.web.nlpmatch;

import java.util.Comparator;

public class ComparatorWord implements Comparator<Word> {

	@Override
	public int compare(Word o1, Word o2) {
		Word word1 = (Word) o1;
		Word word2 = (Word) o2;
		if (word1.getIndex() < word2.getIndex()) {
			return -1;
		} else {
			return 1;
		}
	}

}
