package com.ai.bot.web.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.BotPropertyMapper;
import com.ai.bot.web.service.entity.BotProperty;
import com.ai.bot.web.service.entity.BotPropertyExample;

@Service
@Transactional
public class BotPropertyLogic {

	@Autowired
	private BotPropertyMapper botPropertyMapper;

	public List<BotProperty> getPropertys(String botType) {
		BotPropertyExample example = new BotPropertyExample();
		example.createCriteria().andBotTypeEqualTo(botType);
		List<BotProperty> propertyList = botPropertyMapper.selectByExample(example);
		return propertyList;
	}
}
