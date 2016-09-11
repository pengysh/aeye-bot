package com.a.eye.bot.system;

import com.a.eye.bot.chat.share.entity.ChatMessageInfo;
import com.a.eye.bot.common.base.ShareBotBase;
import com.a.eye.bot.common.base.StateBase;
import com.a.eye.bot.common.message.BotAckMessage;
import com.a.eye.bot.common.message.BotReqMessage;
import com.a.eye.bot.common.message.PromptAckMessage;
import com.a.eye.bot.common.message.actor.ActorRef;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.message.util.MessageIdGenerate;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.a.eye.bot.meetingscene.creator.MeetingSceneBotApi;
import com.a.eye.bot.meetingscene.message.AskMeWithinTimeAckMeetingSceneMessage;
import com.a.eye.bot.meetingscene.message.AskTimeWithinMeAckMeetingSceneMessage;
import com.a.eye.bot.system.message.SystemBotMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class SystemBot extends ShareBotBase {

    private static Logger logger = LogManager.getLogger(SystemBot.class.getName());

    private Gson gson = new Gson();

    private SystemBotMessage reqMessage;

    private PartOfSpeechManager manager = SpringContextUtil.getBean(PartOfSpeechManager.class);

    public SystemBot(String id) {
        super(id, State.Idle);
    }

    class State extends StateBase {
    }

    @Override
    public void call(String reverseMessageId, BotReqMessage message, String reverseSender) {
        logger.debug("机器人收到消息");
        reqMessage = (SystemBotMessage) message;

        MeetingSceneBotApi api = new MeetingSceneBotApi(getContext());
        ActorRef actorRef = api.createBot(reqMessage.getUserId(), reqMessage.getUserName());

        SystemBotMethod method = new SystemBotMethod();
        String sentence = manager.parseMessage(reqMessage.getMessage());

        method.parsePersonContent(reverseMessageId, getSelf(), actorRef, sentence);
    }

    @Override
    public void callBack(String forwardMessageId, List<BotAckMessage> messageList, String forwardSender) {
        logger.debug("桥接机器人收到消息回执");
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setGroupId(reqMessage.getGroupId());
        chatMessageInfo.setMessageId(MessageIdGenerate.generateFirstMessageId());
        chatMessageInfo.setSender(25l);
        chatMessageInfo.setSendTime(new Date().getTime());
        chatMessageInfo.setReceiver(reqMessage.getUserId());

        for (int i = 0; i < messageList.size(); i++) {
            BotAckMessage botAckMessage = messageList.get(i);
            if (botAckMessage instanceof AskMeWithinTimeAckMeetingSceneMessage) {
                AskMeWithinTimeAckMeetingSceneMessage content = (AskMeWithinTimeAckMeetingSceneMessage) botAckMessage;

                chatMessageInfo.setMessage(content.getReplyMessage());
            } else if (botAckMessage instanceof AskTimeWithinMeAckMeetingSceneMessage) {
                AskTimeWithinMeAckMeetingSceneMessage content = (AskTimeWithinMeAckMeetingSceneMessage) botAckMessage;
                logger.error("forwardMessageId:" + forwardMessageId + ",id:" + content.getId());

                chatMessageInfo.setMessage(content.getId() + "有空");
            } else if (botAckMessage instanceof PromptAckMessage) {
                PromptAckMessage content = (PromptAckMessage) botAckMessage;

                chatMessageInfo.setMessage(content.getPromptContent());
            }
            String recevieContentJsonStr = gson.toJson(chatMessageInfo);
            JsonObject recevieContentJson = gson.fromJson(recevieContentJsonStr, JsonObject.class);

            // 用户在线则发送
            Cmd cmd = new Cmd();
            cmd.setCmd("ChatMessageRecevie");
            cmd.setContent(recevieContentJson);

            logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
            CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
            logger.debug("命令执行实例：" + executer.getClass());
            executer.sendMessage(cmd.getContent());
        }
    }
}
