package com.a.eye.bot.user.cache;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;

/**
 * @Title: UserCacheStartUp.java
 * @author: pengysh
 * @date 2016年8月13日 下午5:04:13
 * @Description:用户缓存服务启动类
 */
public class UserCacheStartUp {
	private static Logger logger = LogManager.getLogger(UserCacheStartUp.class.getName());

	public static void main(String args[]) throws InterruptedException {
		logger.debug("应用启动开始");
		SpringApplication application = new SpringApplication(UserCacheStartUp.class);
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
