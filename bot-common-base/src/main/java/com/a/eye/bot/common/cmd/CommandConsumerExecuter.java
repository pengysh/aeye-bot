package com.a.eye.bot.common.cmd;

import com.a.eye.bot.common.message.actor.ActorSystem;
import com.a.eye.bot.common.message.channel.ActorChannel;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.conts.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author: pengysh
 * @date 2016/9/9 0009 下午 5:30
 * @Description
 */
@Component("CommandConsumer")
public class CommandConsumerExecuter extends CmdConsumerExecuter {

    private Logger logger = LogManager.getLogger(CommandConsumerExecuter.class.getName());

    private Gson gson = new Gson();

    @Override
    public void receiveMessage(Long messageId, JsonObject contentJson) {
        String isReply = contentJson.get(Constants.Cmd_Command_Content_Tell_Or_Reply).getAsString();
        String sender = contentJson.get(Constants.Cmd_Command_Content_Sender).getAsString();
        String messageClassName = contentJson.get(Constants.Cmd_Command_Content_Message_ClassName).getAsString();
        String reverseMessageId = contentJson.get(Constants.Cmd_Command_Content_Message_Id).getAsString();
        JsonObject messageJson = contentJson.getAsJsonObject(Constants.Cmd_Command_Content_Message);
        logger.debug("isReply:" + isReply);
        logger.debug("reverseMessageId:" + reverseMessageId);
        logger.debug("sender:" + sender);
        logger.debug("messageClassName:" + messageClassName);
        logger.debug("message:" + messageJson.toString());

        Class<?> messageClass = null;
        try {
            messageClass = Class.forName(messageClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object message = gson.fromJson(messageJson, messageClass);
        String forwardMessageId = ActorChannel.getForwardMessageId(reverseMessageId);
        String reverseSender = ActorChannel.getSender(reverseMessageId);
        String forwardSender = ActorChannel.getSender(forwardMessageId);

        if (Constants.Cmd_Command_Content_Reply.equals(isReply)) {
            ActorChannel.receipt(reverseMessageId);
        }
        ActorSystem.getActor(sender).onReceive(forwardMessageId, reverseMessageId, message, forwardSender, reverseSender);
    }
}
