package com.ai.message.actor;

import java.io.Serializable;

public interface Creator<T> extends Serializable {

	public Actor create();
}
