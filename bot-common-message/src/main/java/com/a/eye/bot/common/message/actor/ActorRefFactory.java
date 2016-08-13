package com.a.eye.bot.common.message.actor;

public abstract class ActorRefFactory {

	public ActorRef actorOf(Props paramProps) {
		ActorRef actorRef = new ActorRef(paramProps.getActorPath());
		ActorSystem.getActor(actorRef.path().getPath()).getContext().pushActorRef(actorRef.path().getPath(), actorRef);
		return actorRef;
	}

}
