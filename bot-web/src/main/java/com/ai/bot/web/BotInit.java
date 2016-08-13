package com.ai.bot.web;

import com.ai.bot.meetingroom.creator.MeetingRoomBotApi;

public class BotInit {

	public void initialize() {
		MeetingRoomBotApi api = new MeetingRoomBotApi(null);
		api.initBots();
	}
}
