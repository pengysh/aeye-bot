package com.ai.bot.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import com.ai.bot.nlp.time.nlp.TimeNormalizer;
import com.ai.bot.web.httpclient.HttpClientUtil;
import com.ai.bot.web.nlpmatch.BotPropertyMatcher;
import com.ai.bot.web.service.entity.BotProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BotPropertyMatcherTest {

	public static void main(String[] args) {
		String text = "我要订后天下午9点到明天上午八点的8个人的没有投影仪的会议室";
		String responseContent = HttpClientUtil.getInstance().sendHttpGet("http://localhost:9999/wordTree?question=" + text);
		Gson gson = new Gson();
		JsonObject wordTreeJson = gson.fromJson(responseContent, JsonObject.class);
		System.out.println(wordTreeJson.get("NodeId") + "\t" + wordTreeJson.get("Word") + "\t" + wordTreeJson.get("PartOfSpeech"));

		BotPropertyMatcher matcher = new BotPropertyMatcher();
		matcher.setPropertyList(initPropertyList());
		matcher.init();

		Map<String, String> matchResult = matcher.botsMatch(wordTreeJson);
		System.out.println("-------------------------------------------------------------------------");
		for (Map.Entry<String, String> entry : matchResult.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
	}

	public static List<BotProperty> initPropertyList() {
		List<BotProperty> propertyList = new ArrayList<BotProperty>();

		BotProperty bots = new BotProperty();
		bots.setBotType("Bots");
		bots.setBotPropertyName("botType");
		bots.setPropertyWords("[{\"Value\":\"MeetingsceneBot\",\"Pattern\":\"会议室|会议\"}]");
		bots.setDataType("BotType");
		bots.setPartOfSpeech("NN");
		propertyList.add(bots);

		BotProperty event = new BotProperty();
		event.setBotType("Bots");
		event.setBotPropertyName("event");
		event.setPropertyWords("[{\"Value\":\"Event_Reserve\",\"Pattern\":\"预定|预订|定|订\"}]");
		event.setDataType("EVENT");
		event.setPartOfSpeech("VV");
		propertyList.add(event);

		BotProperty hasProjector = new BotProperty();
		hasProjector.setBotType("MeetingsceneBot");
		hasProjector.setBotPropertyName("hasProjector");
		hasProjector.setPropertyWords("[{\"Value\":\"true\",\"Pattern\":\"有\"},{\"Value\":\"false\",\"Pattern\":\"没有|没\"}]");
		hasProjector.setDataType("BOOLEAN");
		hasProjector.setPartOfSpeech("VE");
		propertyList.add(hasProjector);

		BotProperty capacity = new BotProperty();
		capacity.setBotType("MeetingsceneBot");
		capacity.setBotPropertyName("capacity");
		capacity.setPropertyWords("[{\"Value\":\"1\",\"Pattern\":\"人|个人\"}]");
		capacity.setDataType("NUMBER");
		capacity.setPartOfSpeech("NN");
		propertyList.add(capacity);

		BotProperty startTime = new BotProperty();
		startTime.setBotType("MeetingsceneBot");
		startTime.setBotPropertyName("startTime");
		startTime.setPropertyWords("[{\"Value\":\"1\",\"Pattern\":\"人|个人\"}]");
		startTime.setDataType("DateTime");
		startTime.setPartOfSpeech("NT");
		propertyList.add(startTime);

		return propertyList;
	}

	public Pattern getTimePattern() {
		String path = TimeNormalizer.class.getResource("").getPath();
		String classPath = path.substring(0, path.indexOf("/com/ai/bot/nlp/time"));
		try {
			return readModel(classPath + "/TimeExp.m");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Pattern readModel(String file) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
		return readModel(in);
	}

	private Pattern readModel(ObjectInputStream in) throws Exception {
		Pattern p = (Pattern) in.readObject();
		// System.out.print(p.pattern());
		return p = Pattern.compile(p.pattern());
	}
}
