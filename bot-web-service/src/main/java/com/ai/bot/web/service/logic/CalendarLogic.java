package com.ai.bot.web.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.CalendarMapper;
import com.ai.bot.web.service.entity.Calendar;
import com.ai.bot.web.service.entity.CalendarExample;
import com.ai.bot.web.service.entity.CalendarExample.Criteria;
import com.google.gson.Gson;

@Service
@Transactional
public class CalendarLogic {

	@Autowired
	private CalendarMapper calendarMapper;

	private Gson gson = new Gson();

	public String getPersonCalendars(String accountId, long startTime, long endTime) {
		CalendarExample calendarExample = new CalendarExample();
		Criteria criteria = calendarExample.createCriteria();
		criteria.andAccountIdEqualTo(accountId);
		criteria.andStarttimeGreaterThanOrEqualTo(startTime);
		criteria.andEndtimeLessThanOrEqualTo(endTime);
		List<Calendar> calendarList = calendarMapper.selectByExample(calendarExample);
		String calendarJsonStr = gson.toJson(calendarList);
		return calendarJsonStr;
	}

	public void insertPersonCalendars(String calendarJson) {
		Calendar calendar = gson.fromJson(calendarJson, Calendar.class);
		calendarMapper.insert(calendar);
	}

	public void updatePersonCalendars(String calendarJson) {
		Calendar calendar = gson.fromJson(calendarJson, Calendar.class);
		calendarMapper.updateByPrimaryKey(calendar);
	}
}
