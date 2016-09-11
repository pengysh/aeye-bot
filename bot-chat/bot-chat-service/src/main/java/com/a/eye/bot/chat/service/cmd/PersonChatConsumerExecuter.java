package com.a.eye.bot.chat.service.cmd;

import com.a.eye.bot.chat.service.service.ChatMessageService;
import com.a.eye.bot.chat.share.content.PersonTalkContent;
import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.chat.share.util.PersonChatGroupIdGen;
import com.a.eye.bot.common.cache.redis.UserStateJedisRepository;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdConsumerExecuter;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.message.util.MessageIdGenerate;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.a.eye.bot.system.creator.SystemBotApi;
import com.a.eye.bot.system.message.SystemBotMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: PersonChatConsumerExecuter.java
 * @author: pengysh
 * @date 2016年8月15日 下午12:07:55
 * @Description:单对单聊天的消费服务
 */
@Component("PersonChatConsumer")
public class PersonChatConsumerExecuter extends CmdConsumerExecuter {

    private Logger logger = LogManager.getLogger(PersonChatConsumerExecuter.class.getName());

    private Gson gson = new Gson();

    @Autowired
    private ChatMessageService service;

    @Autowired
    private UserStateJedisRepository userStateJedisRepository;

    private SystemBotApi systemBotApi = new SystemBotApi();

    /**
     * @param messageId
     * @param contentJson
     * @Title: receiveMessage
     * @author: pengysh
     * @date 2016年8月15日 下午12:07:47
     * @Description:消费
     */
    public void receiveMessage(Long messageId, JsonObject contentJson) {
        logger.debug("收到命令：" + contentJson);
        PersonTalkContent content = gson.fromJson(contentJson.toString(), PersonTalkContent.class);
        String groupId = PersonChatGroupIdGen.gen(content.getSender(), content.getReceiver());
        service.saveMessage(messageId, groupId, content.getSender(), content.getMessage(), content.getSendTime());
        this.sendToReceiver(messageId, groupId, content);

        this.chatWithRobot(content.getSender(), content.getReceiver(), groupId, content.getMessage());
    }

    /**
     * @param content
     * @Title: sendToReceiver
     * @author: pengysh
     * @date 2016年8月15日 下午12:32:41
     * @Description:发送消息到接收者
     */
    private void sendToReceiver(Long messageId, String groupId, PersonTalkContent content) {
        String userState = userStateJedisRepository.selectUserState(content.getReceiver());
        logger.debug("用户：" + content.getReceiver() + "\t 状态：" + userState);

        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setGroupId(groupId);
        chatMessageInfo.setMessage(content.getMessage());
        chatMessageInfo.setMessageId(messageId);
        chatMessageInfo.setSender(content.getSender());
        chatMessageInfo.setSendTime(content.getSendTime());
        chatMessageInfo.setReceiver(content.getReceiver());

        String recevieContentJsonStr = gson.toJson(chatMessageInfo);
        JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);

        // 用户在线则发送
        if (UserStateConstants.Online_State.equals(userState)) {
            Cmd cmd = new Cmd();
            cmd.setCmd("ChatMessageRecevie");
            cmd.setContent(recevieContentJson);

            logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
            CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
            logger.debug("命令执行实例：" + executer.getClass());
            executer.sendMessage(cmd.getContent());
        }
    }

    /**
     * @author: pengysh
     * @date 2016/8/22 0022 下午 12:05
     * @Description：与机器人对话
     */
    private void chatWithRobot(final Long userId, final Long recevie, final String groupId, final String message) {
        if (recevie == 25l) {
            ActorRef systemBotActorRef = systemBotApi.getCache(userId);
            if (systemBotActorRef == null) {
                systemBotActorRef = systemBotApi.createBot(userId);
            }
            logger.debug(systemBotActorRef.path() + "发送消息给会议机器人");
            Long messageId = MessageIdGenerate.generateFirstMessageId();
            systemBotActorRef.tell(String.valueOf(messageId), new SystemBotMessage(userId, "", groupId, message), ActorRef.noSender());

//            String reply = HttpClientUtil.getInstance().sendHttpGet("http://10.19.9.15:8008/chat?question=" + message);
//
//            ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
//            chatMessageInfo.setGroupId(groupId);
//            chatMessageInfo.setMessage(reply);
//            chatMessageInfo.setMessageId(MessageIdGenerate.generateFirstMessageId());
//            chatMessageInfo.setSender(25l);
//            chatMessageInfo.setSendTime(new Date().getTime());
//            chatMessageInfo.setReceiver(userId);
//
//            String recevieContentJsonStr = gson.toJson(chatMessageInfo);
//            JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);
//
//            Cmd cmd = new Cmd();
//            cmd.setCmd("ChatMessageRecevie");
//            cmd.setContent(recevieContentJson);
//
//            logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
//            CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
//            logger.debug("命令执行实例：" + executer.getClass());
//            executer.sendMessage(cmd.getContent());
        }
    }
}
