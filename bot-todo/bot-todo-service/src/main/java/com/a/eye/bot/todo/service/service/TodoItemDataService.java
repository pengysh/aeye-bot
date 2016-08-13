package com.a.eye.bot.todo.service.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.todo.service.dao.TodoItemMapper;
import com.a.eye.bot.todo.service.entity.TodoItem;
import com.a.eye.bot.todo.share.service.ITodoItemDataService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * @Title: TodoItemDataService.java
 * @author: pengysh
 * @date 2016年8月11日 下午3:55:04
 * @Description:待办事项数据操作服务
 */
@Service
@Transactional
public class TodoItemDataService implements ITodoItemDataService {

	private static Logger logger = LogManager.getLogger(TodoItemDataService.class.getName());

	@Autowired
	private TodoItemMapper todoItemMapper;

	/**
	 * @Title: addTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午3:55:31
	 * @Description:创建待办事项
	 * @param userId
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param allDay
	 * @param importance
	 */
	@Override
	public void addTodoItemData(Long userId, String title, Long startTime, Long endTime, Boolean allDay, String importance) {
		logger.debug("创建待办事项：" + userId + "\t" + title + "\t" + startTime + "\t" + endTime + "\t" + allDay + "\t" + importance);
		TodoItem todoItem = new TodoItem();
		todoItem.setUserId(userId);
		todoItem.setTitle(title);
		todoItem.setStartTime(startTime);
		todoItem.setEndTime(endTime);
		todoItem.setAllDay(allDay);
		todoItem.setImportance(importance);
		todoItem.setState(Constants.State_Active);

		todoItemMapper.insert(todoItem);
	}

	/**
	 * @Title: modifyTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:16:56
	 * @Description:变更待办事项
	 * @param id
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param allDay
	 * @param importance
	 */
	@Override
	public void modifyTodoItemData(Long id, String title, Long startTime, Long endTime, Boolean allDay, String importance) {
		logger.debug("变更待办事项：" + id + "\t" + title + "\t" + startTime + "\t" + endTime + "\t" + allDay + "\t" + importance);
		TodoItem todoItem = new TodoItem();
		todoItem.setId(id);
		todoItem.setTitle(title);
		todoItem.setStartTime(startTime);
		todoItem.setEndTime(endTime);
		todoItem.setAllDay(allDay);
		todoItem.setImportance(importance);

		todoItemMapper.updateByPrimaryKeySelective(todoItem);
	}

	/**
	 * @Title: deleteTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:20:27
	 * @Description:作废待办事项
	 * @param id
	 */
	@Override
	public void deleteTodoItemData(Long id) {
		TodoItem todoItem = new TodoItem();
		todoItem.setId(id);
		todoItem.setState(Constants.State_Delete);
		todoItemMapper.updateByPrimaryKeySelective(todoItem);
	}
}
