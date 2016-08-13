package com.ai.bot.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BotPropertyJson {

	public static void main(String[] args) {
		BotPropertyJson.hasProjector();
		BotPropertyJson.event();
		BotPropertyJson.botType();
		BotPropertyJson.capacity();
	}

	private static void event() {
		JsonArray array = new JsonArray();
		JsonObject Event_Reserve = new JsonObject();
		Event_Reserve.addProperty("Value", "Event_Reserve");
		Event_Reserve.addProperty("Pattern", "预定|预订|定|订");
		array.add(Event_Reserve);

		System.out.println(array.toString());
	}
	
	private static void botType() {
		JsonArray array = new JsonArray();
		JsonObject MeetingsceneBot = new JsonObject();
		MeetingsceneBot.addProperty("Value", "MeetingsceneBot");
		MeetingsceneBot.addProperty("Pattern", "会议室|会议");
		array.add(MeetingsceneBot);

		System.out.println(array.toString());
	}
	
	private static void capacity() {
		JsonArray array = new JsonArray();
		JsonObject capacity = new JsonObject();
		capacity.addProperty("Value", "1");
		capacity.addProperty("Pattern", "人|个人");
		array.add(capacity);

		System.out.println(array.toString());
	}
	
	private static void hasProjector() {
		JsonArray array = new JsonArray();
		JsonObject truehasProjector = new JsonObject();
		truehasProjector.addProperty("Value", "true");
		truehasProjector.addProperty("Pattern", "有");
		array.add(truehasProjector);
		
		JsonObject falsehasProjector = new JsonObject();
		falsehasProjector.addProperty("Value", "false");
		falsehasProjector.addProperty("Pattern", "没有|没");
		array.add(falsehasProjector);

		System.out.println(array.toString());
	}
}
