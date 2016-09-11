package com.a.eye.bot.meetingroom.creator;


import com.a.eye.bot.common.base.CreatorBase;
import com.a.eye.bot.meetingroom.MeetingRoomBot;

public class MeetingRoomCreater extends CreatorBase<MeetingRoomBot> {

	private static final long serialVersionUID = 492371702000964028L;

	private String name;
	private int capacity;
	private boolean hasProjector;

	public MeetingRoomCreater(String id, String name, int capacity, boolean hasProjector) {
		super(id);
		this.name = name;
		this.capacity = capacity;
		this.hasProjector = hasProjector;
	}

	@Override
	public MeetingRoomBot create() {
		return new MeetingRoomBot(getId(), name, capacity, hasProjector);
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isHasProjector() {
		return hasProjector;
	}
}
