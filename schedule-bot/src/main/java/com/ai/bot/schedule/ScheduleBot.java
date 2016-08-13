package com.ai.bot.schedule;

import java.util.List;

import com.ai.bot.common.base.PersonalBotBase;
import com.ai.bot.common.base.StateBase;
import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;
import com.ai.bot.schedule.entity.ScheduleEntity;

public class ScheduleBot extends PersonalBotBase {

	private List<ScheduleEntity> contentList;

	public ScheduleBot(String id, String personId, String personName) {
		super(id, personId, personName);
		this.contentList = contentList;
	}

	class State extends StateBase {
	}

	public class Event {
		public static final String Ask = "Ask";
		public static final String Create = "Create";
		public static final String Delete = "Delete";
		public static final String Modify = "Modify";
	}

	@Override
	public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
		// TODO Auto-generated method stub
	}

	@Override
	public void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender) {
		// TODO Auto-generated method stub
	}
}
