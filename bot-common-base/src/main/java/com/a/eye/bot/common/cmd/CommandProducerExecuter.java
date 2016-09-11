package com.a.eye.bot.common.cmd;

import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: pengysh
 * @date 2016/9/10 0010 上午 11:13
 * @Description
 */
@Component("CommandProducer")
public class CommandProducerExecuter extends CmdProducerExecuter {

    private Logger logger = LogManager.getLogger(CommandProducerExecuter.class.getName());

    @Value("${command_topicName}")
    private String topicName;

    @Override
    public void sendMessage(JsonObject contentJson) {
        logger.debug("收到命令：" + contentJson);
        this.producer(contentJson.toString(), "Command", topicName);
    }
}
