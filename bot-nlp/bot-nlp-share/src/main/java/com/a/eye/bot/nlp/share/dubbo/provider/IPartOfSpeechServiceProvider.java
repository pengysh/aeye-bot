package com.a.eye.bot.nlp.share.dubbo.provider;

/**
 * @Title: IPartOfSpeechServiceProvider.java
 * @author: pengysh
 * @date 2016年8月18日 下午7:29:25
 * @Description:词性解析
 */
public interface IPartOfSpeechServiceProvider {

	public String parseMessage(String sentence);
}
