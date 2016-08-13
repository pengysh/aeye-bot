package com.a.eye.bot.main.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Title: MainWebApplication.java
 * @author: pengysh
 * @date 2016年8月10日 下午10:11:21
 * @Description: 系统主界面启动类
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.a.eye.bot.common.ui.config")
public class MainWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(MainWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication.run(MainWebApplication.class, args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
