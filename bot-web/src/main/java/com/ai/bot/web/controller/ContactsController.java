package com.ai.bot.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.bot.web.service.logic.AccountLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/contacts", produces = { "application/json;charset=UTF-8" })
@Api(value = "/contacts", description = "通讯录相关操作")
public class ContactsController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(ContactsController.class.getName());

	@Autowired
	private AccountLogic accountLogic;

	@RequestMapping(value = "/getContacts", method = RequestMethod.GET)
	@ApiOperation(notes = "getContacts", httpMethod = "GET", value = "获取联系人列表")
	@ResponseBody
	public void getContacts(@ApiParam(required = true, value = "contacts data") @CookieValue("CompanyId") String companyId, HttpServletResponse response) throws IOException {
		logger.debug("getContacts" + "\t" + companyId);
		String responseJsonStr = todoItemLogic.getToDoItem(accountId, start, end);
		reply(responseJsonStr, response);
	}
}
