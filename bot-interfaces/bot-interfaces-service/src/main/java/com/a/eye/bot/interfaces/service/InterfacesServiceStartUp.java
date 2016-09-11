package com.a.eye.bot.interfaces.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * @Title: InterfacesServiceStartUp.java
 * @author: pengysh
 * @date 2016年9月9日 下午4:07:27
 * @Description:系统接口后台服务启动
 */
@SpringBootApplication
public class InterfacesServiceStartUp {
	private static Logger logger = LogManager.getLogger(InterfacesServiceStartUp.class.getName());

	public static void main(String args[]) throws Exception {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(InterfacesServiceStartUp.class);
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
