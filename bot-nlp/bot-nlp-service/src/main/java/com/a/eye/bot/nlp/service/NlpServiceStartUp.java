package com.a.eye.bot.nlp.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;

/**
 * @Title: NlpServiceStartUp.java
 * @author: pengysh
 * @date 2016年8月18日 下午7:18:48
 * @Description:词性解析服务启动类
 */
public class NlpServiceStartUp {
	private static Logger logger = LogManager.getLogger(NlpServiceStartUp.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(NlpServiceStartUp.class);
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
