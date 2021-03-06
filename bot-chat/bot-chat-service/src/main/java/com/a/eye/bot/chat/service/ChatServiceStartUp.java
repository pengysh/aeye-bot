package com.a.eye.bot.chat.service;

import com.a.eye.bot.common.lucene.LuceneStore;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * @Title: ChatServiceStartUp.java
 * @author: pengysh
 * @date 2016年8月12日 下午4:07:27
 * @Description:聊天后台服务启动
 */
@SpringBootApplication
public class ChatServiceStartUp {
	private static Logger logger = LogManager.getLogger(ChatServiceStartUp.class.getName());

	public static void main(String args[]) throws Exception {
		logger.debug("应用启动开始");
		LuceneStore.Initialization();

		SpringApplication application = new SpringApplication(ChatServiceStartUp.class);
		application.setWebEnvironment(false);

		Set<Object> set = new HashSet<Object>();
		set.add("classpath:applicationContext-service.xml");
		application.setSources(set);
		application.run(args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
