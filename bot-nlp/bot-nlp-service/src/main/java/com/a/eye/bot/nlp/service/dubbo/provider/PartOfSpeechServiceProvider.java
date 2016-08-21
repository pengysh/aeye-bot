package com.a.eye.bot.nlp.service.dubbo.provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.nlp.service.service.PartOfSpeechService;
import com.a.eye.bot.nlp.share.dubbo.provider.IPartOfSpeechServiceProvider;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * @Title: PartOfSpeechServiceProvider.java
 * @author: pengysh
 * @date 2016年8月18日 下午7:29:07
 * @Description:词性解析
 */
@Service
public class PartOfSpeechServiceProvider implements IPartOfSpeechServiceProvider {

	@Autowired
	private PartOfSpeechService service;

	@Override
	public String parseMessage(String sentence) {
		return service.parseMessage(sentence);
	}

}
