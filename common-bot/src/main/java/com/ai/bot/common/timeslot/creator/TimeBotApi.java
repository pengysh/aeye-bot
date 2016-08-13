package com.ai.bot.common.timeslot.creator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.base.BotApiBase;
import com.ai.bot.common.base.StateBase;
import com.ai.bot.common.util.BotIdGenerate;
import com.ai.bot.common.util.RegistedBot;
import com.ai.bot.common.util.SpringContextUtil;
import com.ai.message.actor.ActorContext;
import com.ai.message.actor.ActorRef;
import com.ai.message.actor.Props;
import com.ai.system.interfaces.entity.MeetingRoomTime;
import com.ai.system.interfaces.logic.MeetingRoomTimeLogic;

public class TimeBotApi extends BotApiBase {

	public TimeBotApi(ActorContext context) {
		super(context);
	}

	private Logger logger = LogManager.getLogger(this.getClass());

	private MeetingRoomTimeLogic logic = SpringContextUtil.getBean(MeetingRoomTimeLogic.class);

	public List<ActorRef> getBot(String ownerId, String ownerType) {
		List<ActorRef> botList = new ArrayList<ActorRef>();

		List<MeetingRoomTime> timeList = logic.getMeetingRoomTime(ownerId);
		for (MeetingRoomTime meetingRoomTime : timeList) {
			TimeBotCreator creator = new TimeBotCreator(BotIdGenerate.generate(RegistedBot.TimeBot), ownerId, ownerType, meetingRoomTime.getStartTime(),
					meetingRoomTime.getEndTime(), meetingRoomTime.getState());
			logger.debug("创建会议室的时间机器人：" + meetingRoomTime.getStartTime() + "---" + meetingRoomTime.getEndTime() + ", state:" + meetingRoomTime.getState());
			ActorRef timeSlotBot = getContext().actorOf(Props.create(creator, RegistedBot.TimeBot.toString()));
			botList.add(timeSlotBot);
		}
		return botList;
	}

	public List<ActorRef> createBot(String ownerId, String ownerType, Date startTime, Date endTime) {
		List<ActorRef> botList = new ArrayList<ActorRef>();
		MeetingRoomTime meetingRoomTime = new MeetingRoomTime();
		meetingRoomTime.setMeetingRoomId(Integer.parseInt(ownerId));
		meetingRoomTime.setStartTime(startTime);
		meetingRoomTime.setEndTime(endTime);
		meetingRoomTime.setState(StateBase.Idle);

		TimeBotCreator creator = new TimeBotCreator(BotIdGenerate.generate(RegistedBot.TimeBot), ownerId, ownerType, meetingRoomTime.getStartTime(), meetingRoomTime.getEndTime(),
				StateBase.Active);
		logger.debug("创建会议室的时间机器人：" + meetingRoomTime.getStartTime() + "---" + meetingRoomTime.getEndTime() + ", state:" + meetingRoomTime.getState());
		ActorRef timeSlotBot = getContext().actorOf(Props.create(creator, RegistedBot.TimeBot.toString()));

		botList.add(timeSlotBot);
		return botList;
	}

	public void reserve(String meetingRoomId, Date startTime, Date endTime) {
		MeetingRoomTime meetingRoomTime = new MeetingRoomTime();
		meetingRoomTime.setMeetingRoomId(Integer.parseInt(meetingRoomId));
		meetingRoomTime.setStartTime(startTime);
		meetingRoomTime.setEndTime(endTime);
		meetingRoomTime.setState(StateBase.Active);
		logic.insertMeetingRoomTime(meetingRoomTime);
	}
}
