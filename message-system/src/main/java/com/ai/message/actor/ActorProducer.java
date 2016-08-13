package com.ai.message.actor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ActorProducer {

	private static String kafkaproperty = "properties/kafka.properties";
	private static Properties properties = new Properties();
	private static KafkaProducer<String, String> producer;

	public void start() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(kafkaproperty);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		producer = new KafkaProducer<String, String>(properties);
	}

	public static KafkaProducer<String, String> getProducer() {
		return producer;
	}
}
