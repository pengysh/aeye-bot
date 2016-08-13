package com.a.eye.bot.common.message.actor;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.a.eye.bot.common.message.channel.ActorChannel;
import com.a.eye.bot.common.message.util.MessageIdGenerate;
import com.google.gson.Gson;

public class ActorRef {

	private Gson gson = new Gson();

	private static final ActorRef noSender = new NoSender(new ActorPath(ActorContants.NoSenderName, null));

	private ActorPath path;

	public ActorRef(ActorPath path) {
		this.path = path;
	}

	public ActorPath path() {
		return path;
	}

	public static ActorRef noSender() {
		return ActorRef.noSender;
	}

	public void tell(String forwardMessageId, Object message, ActorRef actorRef) {
		String reverseMessageId = "";
		reverseMessageId = MessageIdGenerate.generate(forwardMessageId, this.path().getName());

		ActorChannel.pushCallRelation(forwardMessageId, reverseMessageId, actorRef.path().getAddress());

		String messageJsonStr = gson.toJson(message);
		String messageClassName = message.getClass().getName();

		String path = this.path().getPath();
		String content = "N" + "|" + path + "|" + messageClassName + "|" + messageJsonStr;
		ActorProducer.getProducer().send(new ProducerRecord<String, String>("meeting", reverseMessageId, content));
	}

	public void reply(String messageId, Object message, ActorRef actorRef) {
		String messageJsonStr = gson.toJson(message);
		String messageClassName = message.getClass().getName();

		String path = this.path().getPath();
		String content = "Y" + "|" + path + "|" + messageClassName + "|" + messageJsonStr;
		ActorProducer.getProducer().send(new ProducerRecord<String, String>("meeting", messageId, content));
	}

	static class NoSender extends ActorRef {

		public NoSender(ActorPath path) {
			super(path);
		}

	}
}
