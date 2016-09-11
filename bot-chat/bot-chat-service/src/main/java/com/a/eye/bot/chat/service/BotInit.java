package com.a.eye.bot.chat.service;

import com.a.eye.bot.meetingroom.creator.MeetingRoomBotApi;

public class BotInit {

	public void initialize() {
		MeetingRoomBotApi api = new MeetingRoomBotApi(null);
		api.initBots();
	}
}
