package com.a.eye.bot.common.cache;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;

/**
 * @Title: CommonCacheStartUp.java
 * @author: pengysh
 * @date 2016年8月16日 下午5:40:31
 * @Description:缓存服务启动类
 */
public class CommonCacheStartUp {
	private static Logger logger = LogManager.getLogger(CommonCacheStartUp.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(CommonCacheStartUp.class);
		application.setWebEnvironment(false);

		Set<Object> set = new HashSet<Object>();
		set.add("classpath:applicationContext-cache.xml");
		application.setSources(set);
		application.run(args);
		logger.debug("应用启动结束");
		while (true) {
			Thread.sleep(1000 * 60 * 60);
		}
	}
}
