package com.ai.bot.web.nlpmatch.speech;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ai.bot.web.nlpmatch.Word;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CDMatcher {

	private static final String CD = "CD";

	private static Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

	static {
		Gson gson = new Gson();
		String patternsStr = "[{\"Value\":\"1\",\"Pattern\":\"^(0|[1-9][0-9]*)$\"}]";
		JsonArray patternJsonArray = gson.fromJson(patternsStr, JsonArray.class);

		for (int i = 0; i < patternJsonArray.size(); i++) {
			JsonObject patternJson = patternJsonArray.get(i).getAsJsonObject();
			String value = patternJson.get("Value").getAsString();
			String patternStr = patternJson.get("Pattern").getAsString();
			Pattern pattern = Pattern.compile(patternStr);
			patternMap.put(value, pattern);
		}
	}

	public String matcher(Map<Integer, Word> words, int id, int parentId) {
		for (Map.Entry<Integer, Word> entry : words.entrySet()) {
			if (CD.equals(entry.getValue().getSpeech()) && (id == entry.getValue().getParentIndex() || parentId == entry.getValue().getIndex())) {
				String word = entry.getValue().getWord();

				for (Map.Entry<String, Pattern> patternEntry : patternMap.entrySet()) {
					Matcher matcher = patternEntry.getValue().matcher(word);
					if (matcher.matches()) {
						entry.getValue().setUsed(true);
//						System.out.println(word + "\t" + Integer.valueOf(word));
						return word;
					}
				}
			}
		}
		return "-1";
	}
}
