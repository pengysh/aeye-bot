package com.a.eye.bot.todo.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.a.eye.bot.common.util.DateUtil;
import com.a.eye.bot.todo.share.service.ITodoItemDataService;
import com.a.eye.bot.todo.ui.model.ToDoItemDataModel;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/todoitemdata", produces = { "application/json;charset=UTF-8" })
@Api(value = "/todoitemdata", description = "任务项相关数据操作")
public class ToDoItemDataController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(ToDoItemDataController.class.getName());

	@Reference
	private ITodoItemDataService todoItemDataService;

	@RequestMapping(value = "saveTodoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "saveTodoItem", httpMethod = "POST", value = "添加一个新的任务项")
	@ResponseBody
	public void saveTodoItem(Model model, @CookieValue("userId") String userId, @ModelAttribute ToDoItemDataModel toDoItemDataModel, HttpServletResponse response)
			throws IOException {
		String startDate = toDoItemDataModel.getStartDate();
		String startTime = toDoItemDataModel.getStartTime();
		String endDate = toDoItemDataModel.getEndDate();
		String endTime = toDoItemDataModel.getEndTime();
		Long startDateTime = DateUtil.parse(startDate + " " + startTime, DateUtil.todoDateFormat).getTime();
		Long endDateTime = DateUtil.parse(endDate + " " + endTime, DateUtil.todoDateFormat).getTime();
		String title = toDoItemDataModel.getTitle();
		boolean allDay = toDoItemDataModel.isAllDay();
		String importance = toDoItemDataModel.getImportance();
		logger.debug("添加一个新的任务项：" + userId + "\t" + title + "\t" + allDay + "\t" + importance + "\t" + startDate + "\t" + startTime + "\t" + endDate + "\t" + endTime);

		todoItemDataService.addTodoItemData(Long.valueOf(userId), title, startDateTime, endDateTime, allDay, importance);

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "modifyToDoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "modifyToDoItem", httpMethod = "POST", value = "编辑一个任务项")
	@ResponseBody
	public void modifyToDoItem(Model model, @ModelAttribute ToDoItemDataModel toDoItemDataModel, HttpServletResponse response) throws IOException {
		String startDate = toDoItemDataModel.getStartDate();
		String startTime = toDoItemDataModel.getStartTime();
		String endDate = toDoItemDataModel.getEndDate();
		String endTime = toDoItemDataModel.getEndTime();
		Long startDateTime = DateUtil.parse(startDate + " " + startTime, DateUtil.todoDateFormat).getTime();
		Long endDateTime = DateUtil.parse(endDate + " " + endTime, DateUtil.todoDateFormat).getTime();
		Long id = toDoItemDataModel.getId();
		String title = toDoItemDataModel.getTitle();
		boolean allDay = toDoItemDataModel.isAllDay();
		String importance = toDoItemDataModel.getImportance();

		todoItemDataService.modifyTodoItemData(id, title, startDateTime, endDateTime, allDay, importance);

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "deprecatedToDoItem/{itemId}", method = RequestMethod.GET)
	@ApiOperation(notes = "deprecatedToDoItem", httpMethod = "GET", value = "作废一个任务项")
	@ResponseBody
	public void deprecatedToDoItem(@ApiParam(required = true, value = "toDoItem data") @PathVariable Long id, HttpServletResponse response) throws IOException {
		logger.debug("deprecatedToDoItem" + "\t" + id);
		todoItemDataService.deleteTodoItemData(id);
		reply(Constants.EmptyString, response);
	}
}
