package com.a.eye.bot.nlp.machine.match.speech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.a.eye.bot.common.util.DateUtil;
import com.a.eye.bot.nlp.machine.entity.BotProperty;
import com.a.eye.bot.nlp.machine.match.BotPropertyMapper;
import com.a.eye.bot.nlp.machine.match.ComparatorWord;
import com.a.eye.bot.nlp.machine.match.PatternPropertyMapper;
import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.NTResult;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;
import com.a.eye.bot.nlp.machine.time.TimeNlp;
import com.a.eye.bot.nlp.machine.time.nlp.TimeUnit;

public class NTMatcher extends SpeechMatcher {

	private static final String NT = "NT";

	private static List<PatternPropertyMapper> patternList = new ArrayList<PatternPropertyMapper>();

	static {
		List<BotProperty> propertyList = BotPropertyMapper.ntPropertyList;
		for (BotProperty botProperty : propertyList) {
			PatternPropertyMapper mapper = new PatternPropertyMapper();
			mapper.setBotProperty(botProperty);
			patternList.add(mapper);
		}
	}

	public List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId) {
		List<SpeechResult> speechResult = new ArrayList<SpeechResult>();

		List<Word> wordList = new ArrayList<Word>();
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (NT.equals(entry.getValue().getSpeech()) && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				wordList.add(entry.getValue());

				this.findTogetherNTWord(words, wordList, entry);

				DTMatcher dtMatcher = new DTMatcher();
				Word dtWord = dtMatcher.matcher(words, entry.getValue().getIndex(), -1);
				if (dtWord != null) {
					wordList.add(dtWord);
				}
			}
		}

		CCMatcher ccMatcher = new CCMatcher();
		Word ccWord = ccMatcher.matcher(words, wordList);
		if (ccWord != null) {
			wordList.add(ccWord);
		}

		ComparatorWord comparator = new ComparatorWord();
		Collections.sort(wordList, comparator);

		String wordStr = "";
		for (Word word : wordList) {
			wordStr = wordStr + word.getWord();
		}
		System.out.println(wordStr);

		TimeUnit[] timeUnit = TimeNlp.getTimeNormalizer().parse(wordStr);
		for (PatternPropertyMapper mapper : patternList) {
			String pattern = mapper.getBotProperty().getPropertyWords();
			if ("SMALL".equals(pattern)) {
				NTResult result = new NTResult();
				// result.setId(entry.getValue().getIndex());
				// result.setParentId(entry.getValue().getParentIndex());
				result.setWord(wordStr);
				result.setPropertyName(mapper.getBotProperty().getBotPropertyName());
				if (timeUnit.length == 1 || (timeUnit.length == 2 && timeUnit[0].getTime().before(timeUnit[1].getTime()))) {
					result.setValue(DateUtil.parse(timeUnit[0].getTime().getTime()));
				} else if (timeUnit.length == 2) {
					result.setValue(DateUtil.parse(timeUnit[1].getTime().getTime()));
				}
				speechResult.add(result);
			} else if ("BIG".equals(pattern)) {
				NTResult result = new NTResult();
				// result.setId(entry.getValue().getIndex());
				// result.setParentId(entry.getValue().getParentIndex());
				result.setWord(wordStr);
				result.setPropertyName(mapper.getBotProperty().getBotPropertyName());
				if (timeUnit.length == 2) {
					if (timeUnit[0].getTime().before(timeUnit[1].getTime())) {
						result.setValue(DateUtil.parse(timeUnit[1].getTime().getTime()));
					} else {
						result.setValue(DateUtil.parse(timeUnit[0].getTime().getTime()));
					}
				}
				speechResult.add(result);
			}
		}

		return speechResult;
	}

	private void findTogetherNTWord(Map<Integer, Word> words, List<Word> wordList, Map.Entry<Integer, Word> entry) {
		for (Map.Entry<Integer, Word> entryNT : words.entrySet()) {
			if (NT.equals(entryNT.getValue().getSpeech()) && (entry.getValue().getIndex() == entryNT.getValue().getParentIndex())) {
				entryNT.getValue().setUsed(true);
				wordList.add(entryNT.getValue());
				this.findTogetherNTWord(words, wordList, entryNT);
			}
		}
	}
}
