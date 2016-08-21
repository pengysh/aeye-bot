package com.a.eye.bot.nlp.machine.match.speech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.a.eye.bot.nlp.machine.match.Word;
import com.a.eye.bot.nlp.machine.match.result.MResult;
import com.a.eye.bot.nlp.machine.match.result.SpeechResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MMatcher extends SpeechMatcher {

	private static final String M = "M";

	private static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

	static {
		Gson gson = new Gson();
		String patternsStr = "[{\"Value\":\"capacity\",\"Pattern\":\"人|个\"}]";
		JsonArray patternJsonArray = gson.fromJson(patternsStr, JsonArray.class);

		for (int i = 0; i < patternJsonArray.size(); i++) {
			JsonObject patternJson = patternJsonArray.get(i).getAsJsonObject();
			String value = patternJson.get("Value").getAsString();
			String patternStr = patternJson.get("Pattern").getAsString();
			Pattern pattern = Pattern.compile(patternStr);
			patternMap.put(value, pattern);
		}
	}

	public List<SpeechResult> matcher(Map<Integer, Word> words, int id, int parentId) {
		List<SpeechResult> speechResult = new ArrayList<SpeechResult>();
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (M.equals(entry.getValue().getSpeech()) && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				String word = entry.getValue().getWord();

				for (Map.Entry<String, Pattern> patternEntry : patternMap.entrySet()) {
					Matcher matcher = patternEntry.getValue().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
						CDMatcher cdMatcher = new CDMatcher();
						String number = cdMatcher.matcher(words, entry.getValue().getIndex(), entry.getValue().getParentIndex());
//						System.out.println(word + "\t" + number);

						MResult result = new MResult();
						result.setId(entry.getValue().getIndex());
						result.setParentId(entry.getValue().getParentIndex());
						result.setPropertyName(patternEntry.getKey());
						result.setWord(word);
						result.setValue(number);
						speechResult.add(result);
					}
				}
			}
		}
		return speechResult;
	}
}
