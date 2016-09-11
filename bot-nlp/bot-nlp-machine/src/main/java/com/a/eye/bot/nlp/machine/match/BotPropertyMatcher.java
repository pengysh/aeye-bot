package com.a.eye.bot.nlp.machine.match;

import com.a.eye.bot.nlp.machine.entity.BotProperty;
import com.a.eye.bot.nlp.machine.service.BotPropertyService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotPropertyMatcher {

	private static final String ROOT = "ROOT";
	private static final String Value = "Value";
	private static final String Word = "Word";
	private static final String NodeId = "NodeId";
	private static final String PartOfSpeech = "PartOfSpeech";
	private static final String SubNodes = "SubNodes";

	private static final String PropertyValue = "Value";
	private static final String PropertyPattern = "Pattern";

	private static final String Event = "Event";
	private static final String BotType = "botType";

	private static final String Data_Type_Boolean = "BOOLEAN";
	private static final String Data_Type_Boolean_True = "true";
	private static final String Data_Type_Boolean_False = "false";

	private Gson gson = new Gson();

	@Autowired
	private BotPropertyService service;

	private List<BotProperty> propertyList;

	Map<String, Map<String, List<Map<String, PatternInfo>>>> botPropertyMap = new HashMap<String, Map<String, List<Map<String, PatternInfo>>>>();

	public BotPropertyMatcher() {
	}

	public Map<String, String> botsMatch(JsonObject wordTreeJson) {
		Map<String, String> matchResult = new HashMap<String, String>();
		String word = wordTreeJson.get(Word).getAsString();
		String speech = wordTreeJson.get(PartOfSpeech).getAsString();
		Map<String, List<Map<String, BotPropertyMatcher.PatternInfo>>> botsMap = botPropertyMap.get("Bots");
		List<Map<String, BotPropertyMatcher.PatternInfo>> speechList = botsMap.get(speech);

		for (Map<String, BotPropertyMatcher.PatternInfo> propertyMap : speechList) {
			for (Map.Entry<String, BotPropertyMatcher.PatternInfo> entry : propertyMap.entrySet()) {
				PatternInfo patternInfo = this.wordMatch(word, entry.getValue());
				if (patternInfo != null) {
					System.out.println(patternInfo.getPropertyName() + "\t" + entry.getKey());
					matchResult.put(Event, entry.getKey());
				}
			}
		}

		JsonArray subNodes = wordTreeJson.get(SubNodes).getAsJsonArray();
		this.propertyMatch(subNodes, "Bots", matchResult);
		String botType = matchResult.get(BotType);
		this.propertyMatch(subNodes, botType, matchResult);
		return matchResult;
	}

	public void propertyMatch(JsonArray nodes, String botType, Map<String, String> matchResult) {
		for (int i = 0; i < nodes.size(); i++) {
			JsonObject subNode = nodes.get(i).getAsJsonObject();
			String word = subNode.get(Word).getAsString();
			String speech = subNode.get(PartOfSpeech).getAsString();
			System.out.println(word + "\t" + speech);

			Map<String, List<Map<String, BotPropertyMatcher.PatternInfo>>> botsMap = botPropertyMap.get(botType);
			List<Map<String, BotPropertyMatcher.PatternInfo>> speechList = botsMap.get(speech);
			if (speechList != null) {
				for (Map<String, BotPropertyMatcher.PatternInfo> propertyMap : speechList) {
					for (Map.Entry<String, BotPropertyMatcher.PatternInfo> entry : propertyMap.entrySet()) {
						PatternInfo patternInfo = this.wordMatch(word, entry.getValue());
						if (patternInfo != null) {
							System.out.println(patternInfo.getPropertyName() + "\t" + entry.getKey());
							matchResult.put(patternInfo.getPropertyName(), entry.getKey());
						}
					}
				}
			}

			JsonArray subNodes = subNode.get(SubNodes).getAsJsonArray();
			this.propertyMatch(subNodes, botType, matchResult);
		}
	}

	private PatternInfo wordMatch(String word, PatternInfo patternInfo) {
		System.out.println("word:" + word);
		Matcher match = patternInfo.getPattern().matcher(word);
		if (match.matches()) {
			return patternInfo;
		}
		return null;
	}

	public void init() {
		initBotPropertys();
	}

	private void initBotPropertys() {
		for (BotProperty botProperty : propertyList) {
			if (botPropertyMap.containsKey(botProperty.getBotType())) {
				Map<String, List<Map<String, PatternInfo>>> speechMap = botPropertyMap.get(botProperty.getBotType());
				this.initSpeech(botProperty.getBotPropertyName(), botProperty.getDataType(), botProperty.getPropertyWords(), botProperty.getPartOfSpeech(), speechMap);
			} else {
				Map<String, List<Map<String, PatternInfo>>> speechMap = new HashMap<String, List<Map<String, PatternInfo>>>();
				this.initSpeech(botProperty.getBotPropertyName(), botProperty.getDataType(), botProperty.getPropertyWords(), botProperty.getPartOfSpeech(), speechMap);
				botPropertyMap.put(botProperty.getBotType(), speechMap);
			}
		}
	}

	private void initSpeech(String botPropertyName, String dataType, String propertyWords, String partOfSpeech, Map<String, List<Map<String, PatternInfo>>> speechMap) {
		List<Map<String, PatternInfo>> propertyList = this.initPropertyWords(botPropertyName, dataType, propertyWords);
		if (speechMap.containsKey(partOfSpeech)) {
			speechMap.get(partOfSpeech).addAll(propertyList);
		} else {
			speechMap.put(partOfSpeech, propertyList);
		}
	}

	private List<Map<String, PatternInfo>> initPropertyWords(String botPropertyName, String dataType, String propertyWords) {
		List<Map<String, PatternInfo>> propertyList = new ArrayList<Map<String, PatternInfo>>();

		if (StringUtils.isNotBlank(propertyWords)) {
			System.out.println("propertyWords:" + propertyWords);
			JsonArray propertyWordsJson = gson.fromJson(propertyWords, JsonArray.class);
			for (int i = 0; i < propertyWordsJson.size(); i++) {
				Map<String, PatternInfo> propertyWordsMap = new HashMap<String, PatternInfo>();
				JsonObject word = propertyWordsJson.get(i).getAsJsonObject();

				String patternStr = word.get(PropertyPattern).getAsString();

				Pattern pattern;
				if ("DateTime".equals(dataType)) {
//					BotPropertyMatcherTest test = new BotPropertyMatcherTest();
//					pattern = test.getTimePattern();
				} else {
					pattern = Pattern.compile(patternStr);
				}
//				propertyWordsMap.put(word.get(PropertyValue).getAsString(), new PatternInfo(botPropertyName, dataType, pattern));

				propertyList.add(propertyWordsMap);
			}
		}

		return propertyList;
	}

	class PatternInfo {
		private String propertyName;
		private String dataType;
		private Pattern pattern;

		public PatternInfo(String propertyName, String dataType, Pattern pattern) {
			this.propertyName = propertyName;
			this.dataType = dataType;
			this.pattern = pattern;
		}

		public Pattern getPattern() {
			return pattern;
		}

		public String getDataType() {
			return dataType;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}

	public List<BotProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<BotProperty> propertyList) {
		this.propertyList = propertyList;
	}
}
