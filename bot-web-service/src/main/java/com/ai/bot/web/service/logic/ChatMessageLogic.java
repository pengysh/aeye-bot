package com.ai.bot.web.service.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.AccountMapper;
import com.ai.bot.web.service.dao.ChatMessageMapper;
import com.ai.bot.web.service.entity.Account;
import com.ai.bot.web.service.entity.ChatMessage;
import com.ai.bot.web.service.entity.ChatMessageExample;
import com.ai.bot.web.service.entity.ChatMessageExample.Criteria;
import com.ai.bot.web.service.entity.DetailChatMessage;
import com.ai.bot.web.service.util.AccountRefUtil;
import com.ai.bot.web.service.util.Constants;
import com.ai.bot.web.service.view.ChatMessageView;
import com.google.gson.Gson;

@Service
@Transactional
public class ChatMessageLogic {
	
	private Gson gson = new Gson();

	@Autowired
	private ChatMessageMapper chatMessageMapper;

	@Autowired
	private AccountMapper accountMapper;

	public void sendMessage(ChatMessage message) {
		chatMessageMapper.insert(message);
	}

	public String getPersonMessage(String accountId, String personAccountId) {
		String accountRef = AccountRefUtil.getAccountRef(accountId, personAccountId);

		ChatMessageExample condition = new ChatMessageExample();
		Criteria criteria = condition.createCriteria();
		criteria.andAccountRefEqualTo(accountRef);
		List<ChatMessage> messageList = chatMessageMapper.selectByExample(condition);

		if (messageList == null || messageList.size() == 0) {
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setMessageId(1l);
			chatMessage.setAccountRef(accountRef);
			chatMessage.setFromAccount(Constants.SystemAccountId);
			chatMessage.setToAccount(accountId);
			chatMessage.setSendTime(new Date());
			chatMessage.setMessage(Constants.FirstTalkMessage);
			messageList.add(chatMessage);
		}

		List<DetailChatMessage> detailMessageList = this.parseListToDetailList(messageList);
		return gson.toJson(new ChatMessageView(Constants.ChatMessage, detailMessageList));
	}

	public List<DetailChatMessage> parseListToDetailList(List<ChatMessage> messageList) {
		List<DetailChatMessage> detailMessageList = new ArrayList<DetailChatMessage>();
		for (ChatMessage chatMessage : messageList) {
			DetailChatMessage detailChatMessage = this.parseToDetail(chatMessage);
			detailMessageList.add(detailChatMessage);
		}
		return detailMessageList;
	}

	public DetailChatMessage parseToDetail(ChatMessage chatMessage) {
		DetailChatMessage detailChatMessage = new DetailChatMessage(chatMessage);
		Account fromAccount = accountMapper.selectByPrimaryKey(chatMessage.getFromAccount());
		detailChatMessage.setFromAccountName(fromAccount.getName());
		detailChatMessage.setFromAccountHeadImage(fromAccount.getHeadimage());
		return detailChatMessage;
	}
	
	public String createReplyDetailMessage(ChatMessage chatMessage){
		DetailChatMessage detailChatMessage = this.parseToDetail(chatMessage);
		List<DetailChatMessage> messageList = new ArrayList<DetailChatMessage>();
		messageList.add(detailChatMessage);
		String replyMessageStr = gson.toJson(new ChatMessageView(Constants.ChatMessage, messageList));
		return replyMessageStr;
	}
}
