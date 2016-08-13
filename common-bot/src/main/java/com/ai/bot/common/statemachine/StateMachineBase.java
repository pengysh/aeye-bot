package com.ai.bot.common.statemachine;

import java.util.Date;

import com.ai.bot.common.message.BotReqMessage;

public abstract class StateMachineBase {

	public static final String matchEventEquals = "matchEventEquals";

	private boolean isExecute = true;

	private Date startTime;

	private Date endTime;

	private String state;

	private boolean unhandled = true;

	private BotReqMessage ReqMessage;

	StateMachineBase(String state) {
		this.state = state;
	}

	StateMachineBase(Date startTime, Date endTime, String state) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

	public void setIsExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}

	public boolean isExecute() {
		return isExecute;
	}

	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public boolean isUnhandled() {
		return unhandled;
	}

	public void setUnhandled(boolean unhandled) {
		this.unhandled = unhandled;
	}

	public BotReqMessage getReqMessage() {
		return ReqMessage;
	}

	public void setReqMessage(BotReqMessage reqMessage) {
		ReqMessage = reqMessage;
	}
}
