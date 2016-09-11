package com.a.eye.bot.system;

import com.a.eye.bot.nlp.share.dubbo.provider.IPartOfSpeechServiceProvider;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * @author: pengysh
 * @date 2016/9/8 0008 下午 1:22
 * @Description
 */
@Component
public class PartOfSpeechManager {

    @Reference
    private IPartOfSpeechServiceProvider partOfSpeechServiceProvider;

    public String parseMessage(String sentence) {
        return partOfSpeechServiceProvider.parseMessage(sentence);
    }
}
