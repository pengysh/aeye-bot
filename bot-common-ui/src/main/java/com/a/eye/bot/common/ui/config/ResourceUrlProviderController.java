package com.a.eye.bot.common.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * 
 * @Title: ResourceUrlProviderController.java
 * @author: pengysh
 * @date 2016年8月9日 下午11:15:45
 * @Description:处理静态资源URL
 */
@ControllerAdvice
public class ResourceUrlProviderController {

	@Autowired
	private ResourceUrlProvider resourceUrlProvider;

	@ModelAttribute("urls")
	public ResourceUrlProvider urls() {
		return this.resourceUrlProvider;
	}
}
