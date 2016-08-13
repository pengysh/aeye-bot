package com.ai.bot.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.bot.common.util.DateUtil;
import com.ai.bot.web.model.DrogToDoItemModel;
import com.ai.bot.web.model.ToDoItem;
import com.ai.bot.web.service.entity.TodoItem;
import com.ai.bot.web.service.logic.TodoItemLogic;
import com.ai.bot.web.service.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/toDoItem", produces = { "application/json;charset=UTF-8" })
@Api(value = "/toDoItem", description = "任务项相关操作")
public class ToDoItemController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(ToDoItemController.class.getName());

	@Autowired
	private TodoItemLogic todoItemLogic;

	@RequestMapping(value = "addToDoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "addToDoItem", httpMethod = "POST", value = "添加一个新的任务项")
	@ResponseBody
	public void addToDoItem(@ApiParam(required = true, value = "toDoItem data") @RequestBody ToDoItem toDoItem, HttpServletResponse response) throws IOException {
		String startDate = toDoItem.getStartDate();
		String startTime = toDoItem.getStartTime();
		String endDate = toDoItem.getEndDate();
		String endTime = toDoItem.getEndTime();
		Date startDateTime = DateUtil.parse(startDate + " " + startTime, DateUtil.todoDateFormat);
		Date endDateTime = DateUtil.parse(endDate + " " + endTime, DateUtil.todoDateFormat);

		TodoItem todoItemEntity = new TodoItem();
		todoItemEntity.setAccountId(toDoItem.getAccountId());
		todoItemEntity.setTitle(toDoItem.getTitle());
		todoItemEntity.setAllDay(false);
		todoItemEntity.setStartTime(startDateTime.getTime());
		todoItemEntity.setEndTime(endDateTime.getTime());
		todoItemEntity.setImportance("");
		todoItemEntity.setState(Constants.StateActived);
		todoItemLogic.saveTodoItem(todoItemEntity);

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "editToDoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "editToDoItem", httpMethod = "POST", value = "编辑一个任务项")
	@ResponseBody
	public void editToDoItem(@ApiParam(required = true, value = "toDoItem data") @RequestBody ToDoItem toDoItem, HttpServletResponse response) throws IOException {
		String startDate = toDoItem.getStartDate();
		String startTime = toDoItem.getStartTime();
		String endDate = toDoItem.getEndDate();
		String endTime = toDoItem.getEndTime();
		Date startDateTime = DateUtil.parse(startDate + " " + startTime, DateUtil.todoDateFormat);
		Date endDateTime = DateUtil.parse(endDate + " " + endTime, DateUtil.todoDateFormat);

		TodoItem todoItemEntity = new TodoItem();
		todoItemEntity.setItemId(Integer.valueOf(toDoItem.getItemId()));
		todoItemEntity.setAccountId(toDoItem.getAccountId());
		todoItemEntity.setTitle(toDoItem.getTitle());
		todoItemEntity.setAllDay(false);
		todoItemEntity.setStartTime(startDateTime.getTime());
		todoItemEntity.setEndTime(endDateTime.getTime());
		todoItemEntity.setImportance("");
		todoItemEntity.setState(Constants.StateActived);
		todoItemLogic.editTodoItem(todoItemEntity);

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "/deprecatedToDoItem/{itemId}", method = RequestMethod.GET)
	@ApiOperation(notes = "deprecatedToDoItem", httpMethod = "GET", value = "作废一个任务项")
	@ResponseBody
	public void deprecatedToDoItem(@ApiParam(required = true, value = "toDoItem data") @PathVariable String itemId, HttpServletResponse response) throws IOException {
		logger.debug("deprecatedToDoItem" + "\t" + itemId);
		todoItemLogic.deprecatedTodoItem(Integer.valueOf(itemId));
		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "/drogToDoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "drogToDoItem", httpMethod = "POST", value = "移动任务项时更新时间")
	@ResponseBody
	public void drogToDoItem(@ApiParam(required = true, value = "toDoItem data") @RequestBody DrogToDoItemModel model, HttpServletResponse response) throws IOException {
		logger.debug("drogToDoItem");
		todoItemLogic.drogToDoItem(Integer.parseInt(model.getItemId()), model.getDayOffset(), model.getHourOffset(), model.getMinuteOffset());

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "/getToDoItem", method = RequestMethod.GET)
	@ApiOperation(notes = "getToDoItem", httpMethod = "GET", value = "获取指定时间段的待办任务")
	@ResponseBody
	public void getToDoItem(@ApiParam(required = true, value = "toDoItem data") @CookieValue("AccountId") String accountId, @RequestParam("start") String start,
			@RequestParam("end") String end, HttpServletResponse response) throws IOException {
		logger.debug("getMonthToDoItem" + "\t" + accountId + "\t" + start + "\t" + end);
		String responseJsonStr = todoItemLogic.getToDoItem(accountId, start, end);
		reply(responseJsonStr, response);
	}
}
