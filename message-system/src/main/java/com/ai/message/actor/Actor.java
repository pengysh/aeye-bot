package com.ai.message.actor;

import com.ai.message.session.ActorSession;

public abstract class Actor {

	private ActorRef selfRef;

	private ActorSession session = new ActorSession();

	public abstract void onReceive(String forwardMessageId, String reverseMessageId, Object message, String forwardSender, String reverseSender);

	public void unhandled(Object message) {

	}

	protected void setSelf(ActorRef selfRef) {
		this.selfRef = selfRef;
	}

	public ActorRef getSelf() {
		return selfRef;
	}

	public ActorSession getSession() {
		return session;
	}

	public ActorContext getContext() {
		return ActorContext.getInstance();
	}

	public ActorContext context() {
		return ActorContext.getInstance();
	}
}
