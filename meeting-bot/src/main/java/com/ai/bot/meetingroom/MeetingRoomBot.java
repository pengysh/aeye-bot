package com.ai.bot.meetingroom;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.base.BotMethod;
import com.ai.bot.common.base.EventBase;
import com.ai.bot.common.base.ShareBotBase;
import com.ai.bot.common.base.StateBase;
import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;
import com.ai.bot.common.message.UnhandleAckMessage;
import com.ai.bot.common.timeslot.creator.TimeBotApi;
import com.ai.bot.common.timeslot.message.AskMeWithinTimeAckMessage;
import com.ai.bot.common.timeslot.message.AskMeWithinTimeMessage;
import com.ai.bot.common.timeslot.message.AskTimeWithinMeAckMessage;
import com.ai.bot.common.timeslot.message.AskTimeWithinMeMessage;
import com.ai.bot.common.timeslot.message.ReserveAckMessage;
import com.ai.bot.common.timeslot.message.ReserveMessage;
import com.ai.bot.common.util.RegistedBot;
import com.ai.bot.meetingroom.message.AskMeWithinTimeAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskMeWithinTimeMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskTimeWithinMeAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskTimeWithinMeMeetingRoomMessage;
import com.ai.bot.meetingroom.message.ReserveAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.ReserveMeetingRoomMessage;
import com.ai.message.actor.ActorRef;

public class MeetingRoomBot extends ShareBotBase {

	private Logger logger = LogManager.getLogger(this.getClass());

	private String name;
	private int capacity;
	private boolean hasProjector;

	public MeetingRoomBot(String id, String name, int capacity, boolean hasProjector) {
		super(id, State.Idle);
		this.name = name;
		this.capacity = capacity;
		this.hasProjector = hasProjector;

		this.initBot();
	}

	class State extends StateBase {
		public static final String Selected = "Selected";
		public static final String Reserve = "Reserved";
		public static final String Damaged = "Damaged";
	}

	public class Event extends EventBase {
		public static final String Reserve = "Reserve";
	}

	@Override
	public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
		logger.debug("收到消息");
		if (message instanceof AskMeWithinTimeMeetingRoomMessage) {
			AskMeWithinTimeMeetingRoomMessage reqMessage = (AskMeWithinTimeMeetingRoomMessage) message;
			logger.debug("收到消息：" + this.name + "\t" + reqMessage.getCapacity() + "\t" + reqMessage.isHasProjector() + "\t" + this.capacity + "\t" + this.hasProjector);

			if (reqMessage.getCapacity() < this.capacity && (reqMessage.isHasProjector() == this.hasProjector || !reqMessage.isHasProjector())) {
				stm.setUnhandled(false);
				new AskMeWithinTimeEvent().run(reverseMessageId, reqMessage, reverseSender);
			}
		} else if (message instanceof AskTimeWithinMeMeetingRoomMessage) {
			AskTimeWithinMeMeetingRoomMessage reqMessage = (AskTimeWithinMeMeetingRoomMessage) message;
			logger.debug("收到消息：" + this.name + "\t" + reqMessage.getCapacity() + "\t" + reqMessage.isHasProjector() + "\t" + this.capacity + "\t" + this.hasProjector);
			if (reqMessage.getCapacity() < this.capacity && (reqMessage.isHasProjector() == this.hasProjector || !reqMessage.isHasProjector())) {
				stm.setUnhandled(false);
				new AskMeWithinTimeEvent().run(reverseMessageId, reqMessage, reverseSender);
			}
		} else if (message instanceof ReserveMeetingRoomMessage) {
			ReserveMeetingRoomMessage reqMessage = (ReserveMeetingRoomMessage) message;
			stm.setUnhandled(false);
			new ReserveEvent().run(reverseMessageId, reqMessage, reverseSender);
		}

		if (stm.isUnhandled()) {
			logger.error("不满足条件，返回未处理消息");
			getContext().getActorRef(reverseSender).reply(reverseMessageId, new UnhandleAckMessage(), getSelf());
		} else {
			logger.debug(getSelf().path() + "已执行");
		}
	}

	@Override
	public void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender) {
		logger.debug(this.name + "会议室机器人收到消息回执");
		logger.error("messageList size:" + messageList.size());

		for (int i = 0; i < messageList.size(); i++) {
			BotAckMessage botAckMessage = messageList.get(i);
			if (botAckMessage instanceof AskMeWithinTimeAckMessage) {
				AskMeWithinTimeAckMessage reqMessage = (AskMeWithinTimeAckMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",startTime:" + reqMessage.getStartTime() + ",endTime:" + reqMessage.getEndTime() + ",state:"
						+ reqMessage.getState());

				if (i == messageList.size() - 1) {
					logger.debug("会议室机器人开始向上回复消息, forwardSender:" + forwardSender);
					AskMeWithinTimeAckMeetingRoomMessage ackMessage = new AskMeWithinTimeAckMeetingRoomMessage(reqMessage.getId(), this.name);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, ackMessage, getSelf());
				}
			} else if (botAckMessage instanceof AskMeWithinTimeAckMessage) {
				AskTimeWithinMeAckMessage reqMessage = (AskTimeWithinMeAckMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",id:" + reqMessage.getId());

				if (i == messageList.size() - 1) {
					logger.debug("会议室机器人开始向上回复消息, forwardSender:" + forwardSender);
					AskTimeWithinMeAckMeetingRoomMessage ackMessage = new AskTimeWithinMeAckMeetingRoomMessage(getId(), true);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, ackMessage, getSelf());
				}
			} else if (botAckMessage instanceof ReserveAckMessage) {
				ReserveAckMessage reqMessage = (ReserveAckMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",id:" + reqMessage.getId());
				if (i == messageList.size() - 1) {
					logger.debug("会议室机器人开始向上回复消息, forwardSender:" + forwardSender);
					ReserveAckMeetingRoomMessage ackMessage = new ReserveAckMeetingRoomMessage(reqMessage.getId(), true);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, ackMessage, getSelf());
				}
			}
		}
	}

	public class AskTimeWithinMeEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskTimeWithinMeMeetingRoomMessage reqMessage = (AskTimeWithinMeMeetingRoomMessage) message;

			for (ActorRef timeSlotBot : botCache.get(RegistedBot.TimeBot.toString())) {
				timeSlotBot.tell(reverseMessageId, new AskTimeWithinMeMessage(reqMessage.getStartTime(), reqMessage.getEndTime()), getSelf());
			}
		}
	}

	public class AskMeWithinTimeEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskMeWithinTimeMeetingRoomMessage reqMessage = (AskMeWithinTimeMeetingRoomMessage) message;

			for (ActorRef timeSlotBot : botCache.get(RegistedBot.TimeBot.toString())) {
				timeSlotBot.tell(reverseMessageId, new AskMeWithinTimeMessage(reqMessage.getStartTime(), reqMessage.getEndTime()), getSelf());
			}
		}
	}

	public class ReserveEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			ReserveMeetingRoomMessage reqMessage = (ReserveMeetingRoomMessage) message;

			for (ActorRef timeSlotBot : botCache.get(RegistedBot.TimeBot.toString())) {
				timeSlotBot.tell(reverseMessageId, new ReserveMessage(reqMessage.getId()), getSelf());
			}
		}
	}

	private void initBot() {
		TimeBotApi api = new TimeBotApi(getContext());
		List<ActorRef> botList = api.getBot(getId(), RegistedBot.MeetingRoomBot.toString());
		logger.debug("创建的时间机器人个数" + botList.size());

		botCache.put(RegistedBot.TimeBot.toString(), botList);
	}
}
