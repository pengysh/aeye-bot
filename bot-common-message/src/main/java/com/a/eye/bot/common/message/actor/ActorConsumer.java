package com.a.eye.bot.common.message.actor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.a.eye.bot.common.message.dispatch.MessageDispatcher;

public class ActorConsumer extends Thread {
	private Logger logger = LogManager.getLogger(this.getClass());
	private static Properties properties = new Properties();
	private static String kafkaproperty = "properties/kafka.properties";
	private final KafkaConsumer<String, String> consumer;
	private MessageDispatcher messageDispatcher = new MessageDispatcher();

	public ActorConsumer() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(kafkaproperty);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList("meeting"));
	}

	@Override
	public void run() {
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				logger.debug("offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
				try {
					messageDispatcher.dispatch(record.key(), record.value());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(record.value());
				}
			}
		}
	}
}
