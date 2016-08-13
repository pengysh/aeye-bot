package com.ai.bot.web.service.logic;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.common.util.DateUtil;
import com.ai.bot.web.service.dao.TodoItemMapper;
import com.ai.bot.web.service.entity.TodoItem;
import com.ai.bot.web.service.entity.TodoItemExample;
import com.ai.bot.web.service.entity.TodoItemExample.Criteria;
import com.ai.bot.web.service.util.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
@Transactional
public class TodoItemLogic {

	private static Logger logger = LogManager.getLogger(TodoItemLogic.class.getName());

	@Autowired
	private TodoItemMapper todoItemMapper;

	public String getToDoItem(String accountId, String start, String end) {
		TodoItemExample todoItemExample = new TodoItemExample();
		Criteria criteria = todoItemExample.createCriteria();
		criteria.andAccountIdEqualTo(accountId);
		criteria.andStartTimeGreaterThanOrEqualTo(DateUtil.parse(start, DateUtil.dayDateFormat).getTime());
		criteria.andEndTimeLessThanOrEqualTo(DateUtil.parse(end, DateUtil.dayDateFormat).getTime());
		criteria.andStateEqualTo(Constants.StateActived);
		List<TodoItem> todoItemList = todoItemMapper.selectByExample(todoItemExample);

		JsonArray todoItemJsonArray = new JsonArray();
		for (TodoItem todoItem : todoItemList) {
			JsonObject todoItemJson = new JsonObject();
			todoItemJson.addProperty("id", todoItem.getItemId());
			todoItemJson.addProperty("title", todoItem.getTitle());
			todoItemJson.addProperty("allDay", false);
			todoItemJson.addProperty("start", DateUtil.parse(todoItem.getStartTime()));
			todoItemJson.addProperty("end", DateUtil.parse(todoItem.getEndTime()));
			todoItemJsonArray.add(todoItemJson);
			logger.debug(todoItem.getItemId() + "\t" + DateUtil.parse(todoItem.getStartTime()) + "\t" + DateUtil.parse(todoItem.getEndTime()));
		}
		logger.debug(todoItemJsonArray.toString());

		return todoItemJsonArray.toString();
	}

	public void saveTodoItem(TodoItem todoItem) {
		todoItemMapper.insert(todoItem);
	}

	public void editTodoItem(TodoItem todoItem) {
		todoItemMapper.updateByPrimaryKey(todoItem);
	}

	public void drogToDoItem(Integer itemId, Integer dayOffset, Integer hourOffset, Integer minuteOffset) {
		TodoItem todoItem = todoItemMapper.selectByPrimaryKey(itemId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(todoItem.getStartTime());
		calendar.add(Calendar.DAY_OF_MONTH, dayOffset);
		calendar.add(Calendar.HOUR_OF_DAY, hourOffset);
		calendar.add(Calendar.MINUTE, minuteOffset);
		todoItem.setStartTime(calendar.getTimeInMillis());

		calendar.setTimeInMillis(todoItem.getEndTime());
		calendar.add(Calendar.DAY_OF_MONTH, dayOffset);
		calendar.add(Calendar.HOUR_OF_DAY, hourOffset);
		calendar.add(Calendar.MINUTE, minuteOffset);
		todoItem.setEndTime(calendar.getTimeInMillis());
		todoItemMapper.updateByPrimaryKey(todoItem);
	}

	public void deprecatedTodoItem(Integer itemId) {
		TodoItem todoItem = todoItemMapper.selectByPrimaryKey(itemId);
		todoItem.setState(Constants.StateDeprecated);
		todoItemMapper.updateByPrimaryKey(todoItem);
	}
}
