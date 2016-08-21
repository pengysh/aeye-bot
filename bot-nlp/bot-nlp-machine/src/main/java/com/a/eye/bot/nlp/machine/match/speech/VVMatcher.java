package com.a.eye.bot.nlp.machine.match.speech;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.a.eye.bot.nlp.machine.entity.BotProperty;
import com.a.eye.bot.nlp.machine.match.BotPropertyMapper;
import com.a.eye.bot.nlp.machine.match.PatternPropertyMapper;
import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;
import com.a.eye.bot.nlp.machine.match.result.VVResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class VVMatcher extends SpeechMatcher {

	private static final String VV = "VV";

	private static List<PatternPropertyMapper> patternList = new ArrayList<PatternPropertyMapper>();

	static {
		List<BotProperty> propertyList = BotPropertyMapper.vvPropertyList;
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
			if (VV.equals(entry.getValue().getSpeech()) && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				String word = entry.getValue().getWord();

				for (PatternPropertyMapper mapper : patternList) {
					Matcher matcher = mapper.getPattern().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
						VVResult result = new VVResult();
						result.setId(entry.getValue().getIndex());
						result.setParentId(entry.getValue().getParentIndex());
						result.setPropertyName(mapper.getBotProperty().getBotPropertyName());
						result.setWord(word);
						result.setValue(mapper.getValue());
						speechResult.add(result);
					}
				}
			}
		}
		return speechResult;
	}
}
