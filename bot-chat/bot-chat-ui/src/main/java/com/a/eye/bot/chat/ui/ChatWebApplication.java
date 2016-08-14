package com.a.eye.bot.chat.ui;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Title: MainWebApplication.java
 * @author: pengysh
 * @date 2016年8月10日 下午10:11:21
 * @Description: 系统主界面启动类
 */

@SpringBootApplication
@ComponentScan("com.a.eye.bot.chat")
public class ChatWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(ChatWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(ChatWebApplication.class);
		application.setWebEnvironment(true);

		Set<Object> set = new HashSet<Object>();
		set.add("classpath:applicationContext-ui.xml");
		application.setSources(set);
		application.run(args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
