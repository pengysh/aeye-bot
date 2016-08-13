package com.ai.bot.meetingscene;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.base.BotMethod;
import com.ai.bot.common.base.PersonalBotBase;
import com.ai.bot.common.base.StateBase;
import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;
import com.ai.bot.common.message.PromptAckMessage;
import com.ai.bot.common.util.DateUtil;
import com.ai.bot.common.util.RegistedBot;
import com.ai.bot.meetingroom.creator.MeetingRoomBotApi;
import com.ai.bot.meetingroom.message.AskMeWithinTimeAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskMeWithinTimeMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskTimeWithinMeAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.AskTimeWithinMeMeetingRoomMessage;
import com.ai.bot.meetingroom.message.ReserveAckMeetingRoomMessage;
import com.ai.bot.meetingroom.message.ReserveMeetingRoomMessage;
import com.ai.bot.meetingscene.message.AskMeWithinTimeAckMeetingSceneMessage;
import com.ai.bot.meetingscene.message.AskMeWithinTimeMeetingSceneMessage;
import com.ai.bot.meetingscene.message.AskTimeWithinMeAckMeetingSceneMessage;
import com.ai.bot.meetingscene.message.AskTimeWithinMeMeetingSceneMessage;
import com.ai.bot.meetingscene.message.ReserveAckMeetingSceneMessage;
import com.ai.bot.meetingscene.message.ReserveMeetingSceneMessage;
import com.ai.message.actor.ActorRef;
import com.google.gson.JsonObject;

public class MeetingSceneBot extends PersonalBotBase {

	private Logger logger = LogManager.getLogger(this.getClass());

	private String region;

	public MeetingSceneBot(String id, String personId, String personName) {
		super(id, personId, personName);
		initContainBots("beijing");
	}

	class State extends StateBase {
	}

	@Override
	public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
		logger.debug("收到消息");
		if (message instanceof AskMeWithinTimeMeetingSceneMessage) {
			stm.when(State.Idle).goTo(State.Cmding).exe(new AskMeWithinTimeEvent(), reverseMessageId, message, reverseSender);
		} else if (message instanceof AskTimeWithinMeMeetingSceneMessage) {
			stm.when(State.Idle).goTo(State.Cmding).exe(new AskTimeWithinMeEvent(), reverseMessageId, message, reverseSender);
		} else if (message instanceof ReserveMeetingSceneMessage) {
			stm.when(State.Idle).goTo(State.Cmding).exe(new ReserveEvent(), reverseMessageId, message, reverseSender);
		}

		if (stm.isUnhandled()) {
			logger.error("不满足条件，返回未处理消息");
			stm.when(State.Cmding).exe(new StateCmdingReply(), reverseMessageId, message, reverseSender);
			stm.whenNot(State.Cmding).exe(new UnhandleReply(), reverseMessageId, message, reverseSender);
		} else {
			logger.debug(getSelf().path() + "已执行");
		}

		stm.setState(State.Idle);
	}

	@Override
	public void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender) {
		logger.debug("会议室场景机器人收到消息回执，回执消息行数：" + messageList.size());

		String replyMessage = "会议室：";
		for (int i = 0; i < messageList.size(); i++) {
			BotAckMessage botAckMessage = messageList.get(i);
			if (botAckMessage instanceof AskMeWithinTimeAckMeetingRoomMessage) {
				AskMeWithinTimeAckMeetingRoomMessage content = (AskMeWithinTimeAckMeetingRoomMessage) botAckMessage;

				replyMessage = replyMessage + content.getName() + "空闲 " + this.createJs(content.getId()) + "，";
				if (i == messageList.size() - 1) {
					logger.debug("会议室场景机器人开始向上回复消息, forwardSender:" + forwardSender);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, new AskMeWithinTimeAckMeetingSceneMessage(replyMessage), getSelf());
				}
			} else if (botAckMessage instanceof AskTimeWithinMeAckMeetingRoomMessage) {
				AskTimeWithinMeAckMeetingRoomMessage content = (AskTimeWithinMeAckMeetingRoomMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",id:" + content.getId());

				if (i == messageList.size() - 1) {
					logger.debug("会议室场景机器人开始向上回复消息, forwardSender:" + forwardSender);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, new AskTimeWithinMeAckMeetingSceneMessage(getId(), true), getSelf());
				}
			} else if (botAckMessage instanceof ReserveAckMeetingRoomMessage) {
				ReserveAckMeetingRoomMessage content = (ReserveAckMeetingRoomMessage) botAckMessage;
				logger.error("forwardMessageId:" + forwardMessageId + ",id:" + content.getId());

				if (i == messageList.size() - 1) {
					logger.debug("会议室场景机器人开始向上回复消息, forwardSender:" + forwardSender);
					getContext().getActorRef(forwardSender).reply(forwardMessageId, new ReserveAckMeetingSceneMessage("预定成功"), getSelf());
				}
			}
		}
	}

	public class AskTimeWithinMeEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskTimeWithinMeMeetingSceneMessage reqContent = (AskTimeWithinMeMeetingSceneMessage) message;

			for (ActorRef meetingRoomBot : botCache.get(RegistedBot.MeetingRoomBot.toString())) {
				meetingRoomBot.tell(reverseMessageId, new AskTimeWithinMeMeetingRoomMessage(reqContent.getDistrict(), reqContent.getStartTime(), reqContent.getEndTime(),
						reqContent.getCapacity(), reqContent.isHasProjector()), getSelf());
			}
		}
	}

	public class AskMeWithinTimeEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			AskMeWithinTimeMeetingSceneMessage reqContent = (AskMeWithinTimeMeetingSceneMessage) message;

			if (DateUtil.getDistanceTimes(reqContent.getStartTime(), reqContent.getEndTime()) > 120) {
				getContext().getActorRef(reverseSender).reply(reverseMessageId, new PromptAckMessage("预定时间不能超过120分钟"), getSelf());
			} else {
				for (ActorRef meetingRoomBot : botCache.get(RegistedBot.MeetingRoomBot.toString())) {
					meetingRoomBot.tell(reverseMessageId, new AskMeWithinTimeMeetingRoomMessage(reqContent.getDistrict(), reqContent.getStartTime(), reqContent.getEndTime(),
							reqContent.getCapacity(), reqContent.isHasProjector()), getSelf());
				}
			}
		}
	}

	public class ReserveEvent implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			ReserveMeetingSceneMessage reqContent = (ReserveMeetingSceneMessage) message;

			for (ActorRef meetingRoomBot : botCache.get(RegistedBot.MeetingRoomBot.toString())) {
				meetingRoomBot.tell(reverseMessageId, new ReserveMeetingRoomMessage(reqContent.getId()), getSelf());
			}
		}
	}

	private void initContainBots(String region) {
		MeetingRoomBotApi api = new MeetingRoomBotApi(getContext());
		List<ActorRef> meetingRoomRefList = api.getCache(region);
		logger.debug("初始化会议机器人个数:" + meetingRoomRefList.size());
		if (botCache.containsKey(RegistedBot.MeetingRoomBot.toString())) {
			botCache.remove(RegistedBot.MeetingRoomBot.toString());
			botCache.put(RegistedBot.MeetingRoomBot.toString(), meetingRoomRefList);
		} else {
			botCache.put(RegistedBot.MeetingRoomBot.toString(), meetingRoomRefList);
		}
	}

	private String createJs(String id) {
		JsonObject optionJson = new JsonObject();
		optionJson.addProperty("event", "com.ai.bot.meetingscene.message.ReserveMeetingSceneMessage");

		JsonObject content = new JsonObject();
		content.addProperty("id", id);
		optionJson.add("content", content);

		String jsonStr = optionJson.toString();
		logger.debug("jsonStr: " + jsonStr);
		String encodeJsonStr = new String(Base64.encodeBase64(jsonStr.getBytes()));
		String js = "<a href=\"javascript:sendMessage('" + encodeJsonStr + "', 'system')\">预定</a>";
		logger.debug("js: " + js);
		return js;
	}
}
