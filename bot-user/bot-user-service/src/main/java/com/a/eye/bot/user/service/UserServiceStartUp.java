package com.a.eye.bot.user.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;

/**
 * @Title: ServiceStartUp.java
 * @author: pengysh
 * @date 2016年8月9日 上午12:47:46
 * @Description:用户服务启动类
 */
public class UserServiceStartUp {
	private static Logger logger = LogManager.getLogger(UserServiceStartUp.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(UserServiceStartUp.class);
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
