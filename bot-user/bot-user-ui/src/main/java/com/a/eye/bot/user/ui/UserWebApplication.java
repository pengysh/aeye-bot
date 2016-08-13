package com.a.eye.bot.user.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @Title: UserWebApplication.java
 * @author: pengysh
 * @date 2016年8月9日 下午11:21:05
 * @Description:用户服务启动类
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.a.eye.bot.common.ui.config")
@ImportResource(locations = { "classpath:applicationContext-dubbo.xml" })
public class UserWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(UserWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication.run(UserWebApplication.class, args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
