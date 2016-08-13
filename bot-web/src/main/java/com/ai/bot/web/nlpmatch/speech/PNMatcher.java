package com.ai.bot.web.nlpmatch.speech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.bot.web.nlpmatch.Word;
import com.ai.bot.web.nlpmatch.result.PNResult;
import com.ai.bot.web.nlpmatch.result.SpeechResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PNMatcher extends SpeechMatcher {

	private static final String PN = "PN";

	private static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

	static {
		Gson gson = new Gson();
		String patternsStr = "[{\"Value\":\"selfStaffId\",\"Pattern\":\"我\"}]";
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
			if (PN.equals(entry.getValue().getSpeech()) && !entry.getValue().isUsed() && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				String word = entry.getValue().getWord();

				for (Map.Entry<String, Pattern> patternEntry : patternMap.entrySet()) {
					Matcher matcher = patternEntry.getValue().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
						System.out.println(word + "\t" + patternEntry.getKey());
						PNResult result = new PNResult();
						result.setId(entry.getValue().getIndex());
						result.setParentId(entry.getValue().getParentIndex());
						result.setWord(word);
						result.setValue(patternEntry.getKey());
						speechResult.add(result);
					}
				}
			}
		}
		return speechResult;
	}
}
