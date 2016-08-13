package com.ai.bot.web.nlpmatch;

import java.util.ArrayList;
import java.util.List;

import com.ai.bot.meetingscene.message.AskMeWithinTimeMeetingSceneMessage;
import com.ai.bot.web.service.entity.BotProperty;

public class BotPropertyMapper {

	public static List<BotProperty> vvPropertyList = new ArrayList<BotProperty>();
	
	public static List<BotProperty> nnPropertyList = new ArrayList<BotProperty>();
	
	public static List<BotProperty> vePropertyList = new ArrayList<BotProperty>();
	
	public static List<BotProperty> ntPropertyList = new ArrayList<BotProperty>();

	static{
		BotProperty event = new BotProperty();
		event.setBotType("Bots");
		event.setBotPropertyName("event");
		event.setPropertyWords("[{\"Value\":\"com.ai.bot.meetingscene.message.AskMeWithinTimeMeetingSceneMessage\",\"Pattern\":\"预定|预订|定|订\"}]");
		event.setDataType("EVENT");
		event.setPartOfSpeech("VV");
		vvPropertyList.add(event);
		
		BotProperty bots = new BotProperty();
		bots.setBotType("Bots");
		bots.setBotPropertyName("botType");
		bots.setPropertyWords("[{\"Value\":\"MeetingRoom\",\"Pattern\":\"会议室\"},{\"Value\":\"Projector\",\"Pattern\":\"投影仪\"}]");
		bots.setDataType("BotType");
		bots.setPartOfSpeech("NN");
		nnPropertyList.add(bots);
		
		BotProperty hasProjector = new BotProperty();
		hasProjector.setBotType("MeetingsceneBot");
		hasProjector.setBotPropertyName("has");
		hasProjector.setPropertyWords("[{\"Value\":\"true\",\"Pattern\":\"有\"},{\"Value\":\"false\",\"Pattern\":\"没有|没\"}]");
		hasProjector.setDataType("BOOLEAN");
		hasProjector.setPartOfSpeech("VE");
		vePropertyList.add(hasProjector);
		
		BotProperty startTime = new BotProperty();
		startTime.setBotType("MeetingsceneBot");
		startTime.setBotPropertyName("startTime");
		startTime.setPropertyWords("SMALL");
		startTime.setDataType("DateTime");
		startTime.setPartOfSpeech("NT");
		ntPropertyList.add(startTime);
		
		BotProperty endTime = new BotProperty();
		endTime.setBotType("MeetingsceneBot");
		endTime.setBotPropertyName("endTime");
		endTime.setPropertyWords("BIG");
		endTime.setDataType("DateTime");
		endTime.setPartOfSpeech("NT");
		ntPropertyList.add(endTime);
		
	}

	public static List<BotProperty> initPropertyList() {
		List<BotProperty> propertyList = new ArrayList<BotProperty>();

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
}
