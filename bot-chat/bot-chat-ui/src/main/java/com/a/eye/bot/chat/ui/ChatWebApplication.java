package com.a.eye.bot.chat.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @Title: MainWebApplication.java
 * @author: pengysh
 * @date 2016年8月10日 下午10:11:21
 * @Description: 系统主界面启动类
 */
@ComponentScan("com.a.eye.bot.chat.ui,com.a.eye.bot.chat.share,com.a.eye.bot.common.util,com.a.eye.bot.user.share.redis")
@ImportResource("classpath:applicationContext-ui.xml")
@EnableAutoConfiguration
public class ChatWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(ChatWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication.run(ChatWebApplication.class, args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ChatWebApplication.class);
	}
}
