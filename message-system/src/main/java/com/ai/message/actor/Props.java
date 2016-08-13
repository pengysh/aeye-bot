package com.ai.message.actor;

import java.io.Serializable;

import com.ai.message.util.ActorIdGenerate;

public final class Props implements Serializable {

	private static final long serialVersionUID = -1365438765174682337L;

	private ActorPath actorPath;

	public static <T extends Actor> Props create(Creator<T> paramCreator, String actorName) {
		Props props = new Props();
		
		Long actorLongId = ActorIdGenerate.increment(actorName);
		actorName = actorName + String.valueOf(actorLongId);
		
		if (ActorSystem.getActor(actorName) != null) {
			throw new RuntimeException();
		}
		
		Actor actor = paramCreator.create();

		ActorPath actorPath = new ActorPath(actorName, actorName);
		props.setActorPath(actorPath);
		ActorSystem.pushActor(actorName, actor);
		return props;
	}

	public ActorPath getActorPath() {
		return actorPath;
	}

	public void setActorPath(ActorPath actorPath) {
		this.actorPath = actorPath;
	}
}
