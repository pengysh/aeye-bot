package com.ai.bot.common.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.bot.common.channel.BotChatChannel;
import com.ai.bot.common.message.BotAckMessage;
import com.ai.bot.common.message.BotReqMessage;
import com.ai.bot.common.message.CmdingAckMessage;
import com.ai.bot.common.message.UnhandleAckMessage;
import com.ai.bot.common.session.BotSession;
import com.ai.bot.common.statemachine.StateMachine;
import com.ai.message.actor.Actor;
import com.ai.message.actor.ActorRef;

abstract class BotBase extends Actor {

	private Logger logger = LogManager.getLogger(this.getClass());

	private String id;

	public Map<String, List<ActorRef>> botCache = new HashMap<String, List<ActorRef>>();

	protected StateMachine stm;

	public BotChatChannel channel = new BotChatChannel();

	public BotSession session = new BotSession();

	public abstract void call(String reverseMessageId, BotReqMessage message, String reverseSender);

	public abstract void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender);

	public BotBase(String id, String state) {
		this.id = id;
		stm = new StateMachine(state);
	}

	public BotBase(String id, Date startTime, Date endTime, String state) {
		this.id = id;
		stm = new StateMachine(startTime, endTime, state);
	}

	public void onReceive(String forwardMessageId, String reverseMessageId, Object message, String forwardSender, String reverseSender) {
		logger.debug("接到消息，消息实例为：" + message.getClass().getSimpleName());
		if (message instanceof BotReqMessage) {
			logger.debug("判定为请求消息BotReqMessage");
			BotReqMessage botMessage = (BotReqMessage) message;
			this.call(reverseMessageId, botMessage, reverseSender);
		} else if (message instanceof BotAckMessage) {
			logger.debug("判定为应答消息BotAckMessage");
			getSession().push(forwardMessageId, message);

			if (getSession().isFinished(forwardMessageId)) {
				logger.debug("消息全部回复：" + this.getClass().getName());
				List<Object> receipts = getSession().getAckMessageList(forwardMessageId);
				getSession().remove(forwardMessageId);

				List<BotAckMessage> ackList = new ArrayList<BotAckMessage>();
				for (Object receipt : receipts) {
					BotAckMessage ack = (BotAckMessage) receipt;
					ackList.add(ack);
				}
				this.callBack(forwardMessageId, ackList, forwardSender);
				stm.when(StateBase.Cmding).goTo(StateBase.Idle);
			} else {
				logger.debug("channel not finished");
			}
		} else if (message instanceof UnhandleAckMessage) {
			logger.error("收到UnhandleAckMessage消息");
		} else {
			logger.error("消息不是请求，也不是应答，不处理");
			unhandled(message);
		}
	}

	public String getId() {
		return id;
	}

	public class StateCmdingReply implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			getContext().getActorRef(reverseSender).reply(reverseMessageId, new CmdingAckMessage(), getSelf());
		}
	}

	public class UnhandleReply implements BotMethod {
		@Override
		public void run(String reverseMessageId, BotReqMessage message, String reverseSender) {
			getContext().getActorRef(reverseSender).reply(reverseMessageId, new UnhandleAckMessage(), getSelf());
		}
	}
}
