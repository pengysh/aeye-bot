package com.a.eye.bot.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Title: SpringContextUtil.java
 * @author: pengysh
 * @date 2016年8月13日 下午2:40:26
 * @Description:SPRING上下文工具类
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	// Spring上下文环境
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		System.out.println(name);
		return (T) applicationContext.getBean(name);
	}
}
