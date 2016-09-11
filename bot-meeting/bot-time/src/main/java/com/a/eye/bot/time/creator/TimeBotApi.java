package com.a.eye.bot.time.creator;

import com.a.eye.bot.common.base.BotApiBase;
import com.a.eye.bot.common.base.StateBase;
import com.a.eye.bot.common.message.actor.ActorContext;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.message.actor.Props;
import com.a.eye.bot.common.util.BotIdGenerate;
import com.a.eye.bot.common.util.RegistedBot;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.a.eye.bot.interfaces.share.entity.MeetingRoomTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeBotApi extends BotApiBase {

	public TimeBotApi(ActorContext context) {
		super(context);
	}

	private Logger logger = LogManager.getLogger(this.getClass());

	private TimeServiceManager timeServiceManager = SpringContextUtil.getBean(TimeServiceManager.class);

	public List<ActorRef> getBot(String ownerId, String ownerType) {
		List<ActorRef> botList = new ArrayList<ActorRef>();

		List<MeetingRoomTime> timeList = timeServiceManager.getMeetingRoomTime(ownerId);
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
		timeServiceManager.insertMeetingRoomTime(meetingRoomTime);
	}
}
