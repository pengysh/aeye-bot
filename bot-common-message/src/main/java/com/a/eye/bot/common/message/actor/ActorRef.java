package com.a.eye.bot.common.message.actor;

import com.a.eye.bot.common.message.channel.ActorChannel;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.message.conts.Constants;
import com.a.eye.bot.common.message.util.MessageIdGenerate;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActorRef {

    private Logger logger = LogManager.getLogger(ActorRef.class.getName());

    private Gson gson = new Gson();

    private static final ActorRef noSender = new NoSender(new ActorPath(ActorContants.NoSenderName, null));

    private ActorPath path;

    public ActorRef(ActorPath path) {
        this.path = path;
    }

    public ActorPath path() {
        return path;
    }

    public static ActorRef noSender() {
        return ActorRef.noSender;
    }

    public void tell(String forwardMessageId, Object message, ActorRef actorRef) {
        String reverseMessageId = "";
        reverseMessageId = MessageIdGenerate.generate(forwardMessageId, this.path().getName());

        ActorChannel.pushCallRelation(forwardMessageId, reverseMessageId, actorRef.path().getAddress());

        String messageJsonStr = gson.toJson(message);
        JsonObject messageJson = gson.fromJson(messageJsonStr, JsonObject.class);
        String messageClassName = message.getClass().getName();

        String sender = this.path().getPath();

        JsonObject content = new JsonObject();
        content.addProperty(Constants.Cmd_Command_Content_Tell_Or_Reply, Constants.Cmd_Command_Content_Tell);
        content.addProperty(Constants.Cmd_Command_Content_Sender, sender);
        content.addProperty(Constants.Cmd_Command_Content_Message_ClassName, messageClassName);
        content.addProperty(Constants.Cmd_Command_Content_Message_Id, reverseMessageId);
        content.add(Constants.Cmd_Command_Content_Message, messageJson);

        Cmd cmd = new Cmd();
        cmd.setCmd("Command");
        cmd.setContent(content);
        CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
        logger.debug("tell 命令执行实例：" + executer.getClass());
        executer.sendMessage(cmd.getContent());
    }

    public void reply(String messageId, Object message, ActorRef actorRef) {
        String messageJsonStr = gson.toJson(message);
        JsonObject messageJson = gson.fromJson(messageJsonStr, JsonObject.class);
        String messageClassName = message.getClass().getName();

        String sender = this.path().getPath();

        JsonObject content = new JsonObject();
        content.addProperty(Constants.Cmd_Command_Content_Tell_Or_Reply, Constants.Cmd_Command_Content_Reply);
        content.addProperty(Constants.Cmd_Command_Content_Sender, sender);
        content.addProperty(Constants.Cmd_Command_Content_Message_ClassName, messageClassName);
        content.addProperty(Constants.Cmd_Command_Content_Message_Id, messageId);
        content.add(Constants.Cmd_Command_Content_Message, messageJson);

        Cmd cmd = new Cmd();
        cmd.setCmd("Command");
        cmd.setContent(content);
        CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
        logger.debug("reply 命令执行实例：" + executer.getClass());
        executer.sendMessage(cmd.getContent());
    }

    static class NoSender extends ActorRef {
        public NoSender(ActorPath path) {
            super(path);
        }
    }
}
