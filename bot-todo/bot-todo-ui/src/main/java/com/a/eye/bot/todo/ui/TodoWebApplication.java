package com.a.eye.bot.todo.ui;

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
 * @Title: TodoWebApplication.java
 * @author: pengysh
 * @date 2016年8月11日 下午4:57:25
 * @Description:待办事项界面启动类
 */
@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan("com.a.eye.bot.common.ui.config,com.a.eye.bot.common.ui.web")
@ImportResource(locations = { "classpath:applicationContext-dubbo.xml" })
public class TodoWebApplication extends SpringBootServletInitializer {
	private static Logger logger = LogManager.getLogger(TodoWebApplication.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication.run(TodoWebApplication.class, args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
