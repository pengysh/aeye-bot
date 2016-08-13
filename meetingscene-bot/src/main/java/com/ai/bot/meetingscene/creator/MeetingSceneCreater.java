package com.ai.bot.meetingscene.creator;

import com.ai.bot.common.base.CreatorBase;
import com.ai.bot.meetingscene.MeetingSceneBot;

public class MeetingSceneCreater extends CreatorBase<MeetingSceneBot> {

	private static final long serialVersionUID = 492371702000964028L;

	private String personId;
	private String personName;

	public MeetingSceneCreater(String id, String personId, String personName) {
		super(id);
		this.personId = personId;
		this.personName = personName;
	}

	@Override
	public MeetingSceneBot create() {
		return new MeetingSceneBot(getId(), personId, personName);
	}
}
