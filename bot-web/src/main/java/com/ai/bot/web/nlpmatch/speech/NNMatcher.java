package com.ai.bot.web.nlpmatch.speech;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.bot.web.nlpmatch.BotPropertyMapper;
import com.ai.bot.web.nlpmatch.PatternPropertyMapper;
import com.ai.bot.web.nlpmatch.Word;
import com.ai.bot.web.nlpmatch.result.NNResult;
import com.ai.bot.web.nlpmatch.result.SpeechResult;
import com.ai.bot.web.service.entity.BotProperty;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NNMatcher extends SpeechMatcher {

	public static final String NN = "NN";

	private static List<PatternPropertyMapper> patternList = new ArrayList<PatternPropertyMapper>();

	static {
		List<BotProperty> propertyList = BotPropertyMapper.nnPropertyList;
		for (BotProperty botProperty : propertyList) {
			Gson gson = new Gson();
			String patternsStr = botProperty.getPropertyWords();
			JsonArray patternJsonArray = gson.fromJson(patternsStr, JsonArray.class);

			for (int i = 0; i < patternJsonArray.size(); i++) {
				PatternPropertyMapper mapper = new PatternPropertyMapper();
				mapper.setBotProperty(botProperty);
				JsonObject patternJson = patternJsonArray.get(i).getAsJsonObject();
				String value = patternJson.get("Value").getAsString();
				String patternStr = patternJson.get("Pattern").getAsString();
				Pattern pattern = Pattern.compile(patternStr);
				mapper.setValue(value);
				mapper.setPattern(pattern);
				patternList.add(mapper);
			}
		}
	}

	public List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId) {
		List<SpeechResult> speechResult = new ArrayList<SpeechResult>();
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (NN.equals(entry.getValue().getSpeech()) && !entry.getValue().isUsed()) {
				String word = entry.getValue().getWord();

				for (PatternPropertyMapper mapper : patternList) {
					Matcher matcher = mapper.getPattern().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
						NNResult result = new NNResult();
						result.setId(entry.getValue().getIndex());
						result.setParentId(entry.getValue().getParentIndex());
						result.setPropertyName(mapper.getBotProperty().getBotPropertyName());
						result.setWord(word);
						result.setValue(mapper.getValue());
						result.setSpeech(NN);
						speechResult.add(result);
					}
				}
			}
		}
		return speechResult;
	}

	public SpeechResult matcherFirst(Map<Integer, Word> words, int id, int parentId) {
		List<SpeechResult> speechResultList = this.matcher(words, id, parentId);

		SpeechResult speechResult = null;
		for (SpeechResult result : speechResultList) {
			if (speechResult == null) {
				speechResult = result;
			} else if (result.getParentId() < speechResult.getParentId()) {
				speechResult = result;
			}
		}

		if (speechResultList != null && speechResultList.size() > 0) {
			for (Map.Entry<Integer, Word> entry : words.entrySet()) {
				if (speechResult.getId() != entry.getKey()) {
					entry.getValue().setUsed(false);
				}
			}
		}
		return speechResult;
	}
}