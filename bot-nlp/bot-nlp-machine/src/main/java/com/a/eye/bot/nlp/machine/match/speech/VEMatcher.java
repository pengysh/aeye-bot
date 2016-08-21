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
import com.a.eye.bot.nlp.machine.match.result.VEResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class VEMatcher extends SpeechMatcher {

	private static final String VE = "VE";

	private static List<PatternPropertyMapper> patternList = new ArrayList<PatternPropertyMapper>();

	static {
		List<BotProperty> propertyList = BotPropertyMapper.vePropertyList;
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
			if (VE.equals(entry.getValue().getSpeech()) && !entry.getValue().isUsed() && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				String word = entry.getValue().getWord();

				for (PatternPropertyMapper mapper : patternList) {
					Matcher matcher = mapper.getPattern().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
						NNMatcher nnMatcher = new NNMatcher();

						SpeechResult nnSpeechResult = new SpeechResult();
						List<SpeechResult> nnSpeechList = nnMatcher.matcher(words, entry.getValue().getIndex(), entry.getValue().getParentIndex());
						if (nnSpeechList != null) {
							nnSpeechResult = nnSpeechList.get(0);
						}

						VEResult result = new VEResult();
						result.setId(entry.getValue().getIndex());
						result.setParentId(entry.getValue().getParentIndex());
						result.setPropertyName(mapper.getBotProperty().getBotPropertyName() + nnSpeechResult.getValue());
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
