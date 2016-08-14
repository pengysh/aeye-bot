package com.a.eye.bot.source.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Title: SourceWebApplication.java
 * @author: pengysh
 * @date 2016年8月11日 下午5:04:49
 * @Description:公共资源界面服务启动类（CSS,JS,IMG等）
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.a.eye.bot.common.ui.config,com.a.eye.bot.common.ui.web")
public class SourceWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(SourceWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication.run(SourceWebApplication.class, args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
