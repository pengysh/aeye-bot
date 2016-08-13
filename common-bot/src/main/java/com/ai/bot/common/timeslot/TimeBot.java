package com.ai.bot.common.timeslot;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.base.BotMethod;
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

public class TimeBot extends ShareBotBase {

	private Logger logger = LogManager.getLogger(this.getClass());

	private String ownerId;
	private String ownerType;

	public TimeBot(String id, String ownerId, String ownerType, Date startTime, Date endTime, String state) {
		super(id, startTime, endTime, state);
		this.ownerId = ownerId;
		this.ownerType = ownerType;
	}

	class State extends StateBase {
	}

	@Override
	public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
		logger.debug("时间机器人收到消息：reverseMessageId:" + reverseMessageId + ",reverseSender:" + reverseSender);
		if (message instanceof AskMeWithinTimeMessage) {
			AskMeWithinTimeMessage reqMessage = (AskMeWithinTimeMessage) message;
			stm.matchTimePoint(reqMessage.getStartTime(), reqMessage.getEndTime()).when(State.Idle).exe(new IdleStateMeWithinTimeReply(), reverseMessageId, message, reverseSender);
		} else if (message instanceof AskTimeWithinMeMessage) {
			AskTimeWithinMeMessage reqMessage = (AskTimeWithinMeMessage) message;
			stm.matchTimeSlot(reqMessage.getStartTime(), reqMessage.getEndTime()).when(State.Idle).exe(new IdleStateTimeWithinMeReply(), reverseMessageId, message, reverseSender);
		} else if (message instanceof ReserveMessage) {
			ReserveMessage reqMessage = (ReserveMessage) message;
			stm.matchId(reqMessage.getId(), this.getId()).when(State.Idle).exe(new IdleStateReserveReply(), reverseMessageId, reqMessage, reverseSender);
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
	}

	class IdleStateMeWithinTimeReply implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskMeWithinTimeAckMessage reqMessage = new AskMeWithinTimeAckMessage(getId(), stm.getStartTime(), stm.getEndTime(), stm.getState());
			getContext().getActorRef(reverseSender).reply(reverseMessageId, reqMessage, getSelf());
		}
	}

	class IdleStateTimeWithinMeReply implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskTimeWithinMeAckMessage reqMessage = new AskTimeWithinMeAckMessage(getId(), true);
			getContext().getActorRef(reverseSender).reply(reverseMessageId, reqMessage, getSelf());
		}
	}

	class IdleStateReserveReply implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			stm.setState(State.Active);
			TimeBotApi api = new TimeBotApi(getContext());
			api.reserve(getOwnerId(), stm.getStartTime(), stm.getEndTime());

			ReserveAckMessage reqMessage = new ReserveAckMessage(getId(), true);
			getContext().getActorRef(reverseSender).reply(reverseMessageId, reqMessage, getSelf());
		}
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getOwnerType() {
		return ownerType;
	}
}
