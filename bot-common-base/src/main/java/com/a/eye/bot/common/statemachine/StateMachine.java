package com.a.eye.bot.common.statemachine;

import com.a.eye.bot.common.base.BotMethod;
import com.a.eye.bot.common.message.BotReqMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class StateMachine extends StateMachineBase {

	private static Logger logger = LogManager.getLogger(StateMachine.class.getName());

	public StateMachine(String state) {
		super(state);
	}

	public StateMachine(Date startTime, Date endTime, String state) {
		super(startTime, endTime, state);
	}

	public StateMachine matchId(String id, String myId) {
		if (this.isExecute() && myId.equals(id)) {
			this.setIsExecute(true);
		} else {
			this.setIsExecute(false);
		}
		return this;
	}

	public StateMachine matchTimeSlot(Date startTime, Date endTime) {
		logger.debug("matchTimeSlot isExecute:" + this.isExecute() + ",startTime: " + this.getStartTime() + ",endTime: " + this.getEndTime());
		logger.debug("matchTimeSlot message startTime: " + startTime + ",endTime: " + endTime);
		if (this.isExecute() && startTime.before(this.getStartTime()) && endTime.after(this.getEndTime())) {
			this.setIsExecute(true);
		} else {
			this.setIsExecute(false);
		}
		return this;
	}

	public StateMachine matchTimePoint(Date startTime, Date endTime) {
		logger.debug("matchTimePoint isExecute:" + this.isExecute() + ",startTime: " + this.getStartTime() + ",endTime: " + this.getEndTime());
		logger.debug("matchTimePoint message startTime: " + startTime + ",endTime: " + endTime);
		if (this.isExecute() && this.getStartTime().before(startTime) && this.getEndTime().after(endTime)) {
			this.setIsExecute(true);
		} else {
			this.setIsExecute(false);
		}
		return this;
	}

	public StateMachine when(String state) {
		logger.debug("when isExecute:" + this.isExecute());
		if (this.isExecute() && state.equals(this.getState())) {
			this.setIsExecute(true);
		} else {
			this.setIsExecute(false);
		}
		return this;
	}

	public StateMachine whenNot(String state) {
		logger.debug("whenNot isExecute:" + this.isExecute());
		if (this.isExecute() && !state.equals(this.getState())) {
			this.setIsExecute(true);
		} else {
			this.setIsExecute(false);
		}
		return this;
	}

	public StateMachine goTo(String state) {
		if (this.isExecute()) {
			this.setState(state);
		}
		return this;
	}

	public void exe(BotMethod methodClass, String reverseMessageId, BotReqMessage message, String reverseSender) {
		logger.debug("exe isExecute: " + this.isExecute());
		if (this.isExecute()) {
			methodClass.run(reverseMessageId, message, reverseSender);
			this.setUnhandled(false);
		}
		this.setIsExecute(true);
	}
}
