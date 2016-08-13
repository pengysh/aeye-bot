package com.ai.bot.web.swagger;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class ApplicationSwaggerConfig {

	@Bean
	public Docket addUserDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		Contact contact = new Contact("彭勇升", "", "pengys5@asiainfo.com");
		ApiInfo apiInfo = new ApiInfo("会议预定系统", "API Document管理", "0.0.1", "www.asiainfo.com", contact, "", "");
		docket.apiInfo(apiInfo);
		return docket;
	}
}
