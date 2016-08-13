package com.a.eye.bot.todo.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.a.eye.bot.todo.share.service.ITodoItemService;
import com.a.eye.bot.todo.ui.model.DrogToDoItemModel;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/todoitem", produces = { "application/json;charset=UTF-8" })
@Api(value = "/todoitem", description = "任务项相关操作")
public class ToDoItemController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(ToDoItemController.class.getName());

	@Reference
	private ITodoItemService todoItemService;

	@RequestMapping(value = "drogToDoItem", method = RequestMethod.POST)
	@ApiOperation(notes = "drogToDoItem", httpMethod = "POST", value = "移动任务项时更新时间")
	@ResponseBody
	public void drogToDoItem(Model model, @ModelAttribute DrogToDoItemModel drogToDoItemModel, HttpServletResponse response) throws IOException {
		Long id = drogToDoItemModel.getId();
		Integer dayOffset = drogToDoItemModel.getDayOffset();
		Integer hourOffset = drogToDoItemModel.getHourOffset();
		Integer minuteOffset = drogToDoItemModel.getMinuteOffset();
		logger.debug("移动任务项时更新时间：" + id + "\t" + dayOffset + "\t" + hourOffset + "\t" + minuteOffset);

		todoItemService.drogUserToDoItem(id, dayOffset, hourOffset, minuteOffset);

		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "getUserToDoItem", method = RequestMethod.GET)
	@ApiOperation(notes = "getUserToDoItem", httpMethod = "GET", value = "获取指定时间段的待办任务")
	@ResponseBody
	public void getUserToDoItem(@ApiParam(required = true, value = "toDoItem data") @CookieValue("userId") String userId, @RequestParam("start") String start,
			@RequestParam("end") String end, HttpServletResponse response) throws IOException {
		logger.debug("getMonthToDoItem" + "\t" + userId + "\t" + start + "\t" + end);
		String responseJsonStr = todoItemService.getUserToDoItem(Long.valueOf(userId), start, end);
		reply(responseJsonStr, response);
	}
}
