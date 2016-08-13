package com.a.eye.bot.common.message.actor;

import java.io.Serializable;

public interface Creator<T> extends Serializable {

	public Actor create();
}
