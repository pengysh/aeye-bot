package com.ai.bot.common.timeslot.creator;

import java.util.Date;

import com.ai.bot.common.base.CreatorBase;
import com.ai.bot.common.timeslot.TimeBot;

public class TimeBotCreator extends CreatorBase<TimeBot> {

	private static final long serialVersionUID = -8826991801394570105L;

	private String ownerId;
	private String ownerType;
	private Date startTime;
	private Date endTime;
	private String state;

	public TimeBotCreator(String id, String ownerId, String ownerType, Date startTime, Date endTime, String state) {
		super(id);
		this.ownerId = ownerId;
		this.ownerType = ownerType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

	@Override
	public TimeBot create() {
		return new TimeBot(this.getId(), ownerId, ownerType, startTime, endTime, state);
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getState() {
		return state;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getOwnerType() {
		return ownerType;
	}
}
