package com.ai.bot.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.bot.common.util.DateUtil;
import com.ai.bot.web.service.logic.CalendarLogic;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(value = "/calendar", produces = { "application/json;charset=UTF-8" })
@Api(value = "/calendar", description = "日历组件")
public class CalendarController {

	private static Logger logger = LogManager.getLogger(CalendarController.class.getName());

	@Autowired
	private CalendarLogic calendarLogic;

	@RequestMapping(value = "/getPersonCalendars", method = RequestMethod.GET)
	@ResponseBody
	public void getPersonCalendars(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("getPersonCalendars");
		String accountId = request.getParameter("accountId");
		accountId = "pengysh";
		String startTimeStr = request.getParameter("start");
		String endTimeStr = request.getParameter("end");
		long startTime = DateUtil.parse(startTimeStr, DateUtil.todoDateFormat).getTime();
		long endTime = DateUtil.parse(endTimeStr, DateUtil.todoDateFormat).getTime();
		String responseStr = calendarLogic.getPersonCalendars(accountId, startTime, endTime);

		reply(responseStr, response);
	}

	private void reply(String result, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter writer = response.getWriter();
		writer.write(result);
	}
}
