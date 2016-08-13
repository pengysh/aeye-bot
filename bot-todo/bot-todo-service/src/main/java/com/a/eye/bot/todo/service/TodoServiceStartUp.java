package com.a.eye.bot.todo.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: TodoServiceStartUp.java
 * @author: pengysh
 * @date 2016年8月11日 下午4:42:53
 * @Description:待办服务启动类
 */
@SpringBootApplication
public class TodoServiceStartUp {
	private static Logger logger = LogManager.getLogger(TodoServiceStartUp.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(TodoServiceStartUp.class);
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
