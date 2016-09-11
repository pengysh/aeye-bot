package com.a.eye.bot.system.creator;

import com.a.eye.bot.common.base.CreatorBase;
import com.a.eye.bot.system.SystemBot;

public class SystemBotCreater extends CreatorBase<SystemBot> {

	private static final long serialVersionUID = 492371702000964028L;

	public SystemBotCreater(String id) {
		super(id);
	}

	@Override
	public SystemBot create() {
		return new SystemBot(getId());
	}

}
