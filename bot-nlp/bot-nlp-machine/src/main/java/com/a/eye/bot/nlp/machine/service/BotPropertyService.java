package com.a.eye.bot.nlp.machine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.eye.bot.nlp.machine.dao.BotPropertyMapper;
import com.a.eye.bot.nlp.machine.entity.BotProperty;

@Service
@Transactional
public class BotPropertyService {

	@Autowired
	private BotPropertyMapper botPropertyMapper;

	public List<BotProperty> getPropertys(String botType) {
		List<BotProperty> propertyList = botPropertyMapper.selectByBotType(botType);
		return propertyList;
	}
}
