package com.a.eye.bot.common.message.actor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ActorProducer {

	private static String kafkaproperty = "properties/kafka.properties";
	private static Properties properties = new Properties();
	private static KafkaProducer<Long, String> producer;

	public void start() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(kafkaproperty);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		producer = new KafkaProducer<Long, String>(properties);
	}

	public static KafkaProducer<Long, String> getProducer() {
		return producer;
	}
}
