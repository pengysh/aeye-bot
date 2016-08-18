package com.a.eye.bot.chat.ui.websocket.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.a.eye.bot.chat.share.content.GetChatRecordContent;
import com.a.eye.bot.chat.share.util.PersonChatGroupIdGen;
import com.a.eye.bot.common.message.cmd.CmdProducerExecuter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Title: GetChatRecordProducerExecuter.java
 * @author: pengysh
 * @date 2016年8月16日 上午10:06:52
 * @Description:获取聊天记录
 */
@Component("GetChatRecordProducer")
public class GetChatRecordProducerExecuter extends CmdProducerExecuter {

	private Logger logger = LogManager.getLogger(GetChatRecordProducerExecuter.class.getName());

	private Gson gson = new Gson();

	@Value("${ui_service_topicName}")
	private String topicName;

	private static final String FromSendTime_Param = "fromSendTime";
	private static final String UserId_Param = "userId";
	private static final String ChatAboutId_Param = "chatAboutId";
	private static final String Is_Topic_Param = "isTopic";
	private static final String NewOrHis_Param = "newOrHis";

	public void sendMessage(JsonObject contentJson) {
		logger.debug("收到命令：" + contentJson);
		GetChatRecordContent content = new GetChatRecordContent();
		content.setUserId(contentJson.get(UserId_Param).getAsLong());
		content.setNewOrHis(contentJson.get(NewOrHis_Param).getAsString());

		boolean isTopic = contentJson.get(Is_Topic_Param).getAsBoolean();
		String chatAboutId = contentJson.get(ChatAboutId_Param).getAsString();
		if (!isTopic) {
			String[] chatAboutIds = chatAboutId.split("-");
			chatAboutId = PersonChatGroupIdGen.gen(Long.valueOf(chatAboutIds[0]), Long.valueOf(chatAboutIds[1]));
		}
		content.setChatAboutId(chatAboutId);
		content.setFromSendTime(contentJson.get(FromSendTime_Param).getAsLong());

		this.producer(gson.toJson(content), "GetChatRecord", topicName);
	}
}
