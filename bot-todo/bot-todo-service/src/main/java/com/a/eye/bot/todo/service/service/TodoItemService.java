package com.a.eye.bot.todo.service.service;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.util.DateUtil;
import com.a.eye.bot.todo.service.dao.TodoItemMapper;
import com.a.eye.bot.todo.service.entity.TodoItem;
import com.a.eye.bot.todo.share.service.ITodoItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @Title: TodoItemService.java
 * @author: pengysh
 * @date 2016年8月11日 下午4:24:57
 * @Description:待办事项服务
 */
@Service
@Transactional
public class TodoItemService implements ITodoItemService {

	private static Logger logger = LogManager.getLogger(TodoItemService.class.getName());

	@Autowired
	private TodoItemMapper todoItemMapper;

	/**
	 * @Title: getToDoItem
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:24:42
	 * @Description:获取指定人员的待办事项清单
	 * @param accountId
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public String getUserToDoItem(Long userId, String start, String end) {
		logger.debug("获取指定人员的待办事项清单：" + userId + "\t" + start + "\t" + end);
		Long startTime = DateUtil.parse(start, DateUtil.dayDateFormat).getTime();
		Long endTime = DateUtil.parse(end, DateUtil.dayDateFormat).getTime();
		List<TodoItem> todoItemList = todoItemMapper.selectUserTodoItem(userId, startTime, endTime, Constants.State_Active);

		JsonArray todoItemJsonArray = new JsonArray();
		for (TodoItem todoItem : todoItemList) {
			JsonObject todoItemJson = new JsonObject();
			todoItemJson.addProperty("id", todoItem.getId());
			todoItemJson.addProperty("title", todoItem.getTitle());
			todoItemJson.addProperty("allDay", false);
			todoItemJson.addProperty("start", DateUtil.parse(todoItem.getStartTime()));
			todoItemJson.addProperty("end", DateUtil.parse(todoItem.getEndTime()));
			todoItemJsonArray.add(todoItemJson);
			logger.debug(todoItem.getId() + "\t" + DateUtil.parse(todoItem.getStartTime()) + "\t" + DateUtil.parse(todoItem.getEndTime()));
		}
		logger.debug(todoItemJsonArray.toString());

		return todoItemJsonArray.toString();
	}

	/**
	 * @Title: drogUserToDoItem
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:40:58
	 * @Description:拖动待办事项时变更待办事项的开始时间和结束时间
	 * @param id
	 * @param dayOffset
	 * @param hourOffset
	 * @param minuteOffset
	 */
	@Override
	public void drogUserToDoItem(Long id, Integer dayOffset, Integer hourOffset, Integer minuteOffset) {
		TodoItem todoItem = todoItemMapper.selectByPrimaryKey(id);
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
}
