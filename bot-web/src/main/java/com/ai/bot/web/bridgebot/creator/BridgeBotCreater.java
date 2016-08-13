package com.ai.bot.web.bridgebot.creator;

import com.ai.bot.common.base.CreatorBase;
import com.ai.bot.web.bridgebot.BridgeBot;

public class BridgeBotCreater extends CreatorBase<BridgeBot> {

	private static final long serialVersionUID = 492371702000964028L;

	public BridgeBotCreater(String id) {
		super(id);
	}

	@Override
	public BridgeBot create() {
		return new BridgeBot(getId());
	}

}
