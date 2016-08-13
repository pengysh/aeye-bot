package com.ai.bot.web.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.bot.web.service.entity.Account;
import com.ai.bot.web.service.logic.AccountLogic;
import com.ai.bot.web.service.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/auth", produces = { "application/json;charset=UTF-8" })
@Api(value = "/auth", description = "任务项相关操作")
public class LoginController {

	private static Logger logger = LogManager.getLogger(LoginController.class.getName());

	@Autowired
	private AccountLogic accountLogic;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(notes = "login", httpMethod = "POST", value = "登录")
	@ResponseBody
	public ModelAndView login(@ApiParam(required = true, value = "toDoItem data") @RequestBody String accountId, @RequestBody String password, HttpServletResponse response)
			throws IOException {
		logger.debug("login");
		Account account = accountLogic.query(accountId, password);

		if (account != null && account.getPassword().equals(password)) {
			Cookie accountCookie = new Cookie(Constants.AccountId, accountId);
			accountCookie.setMaxAge(-1);
			accountCookie.setPath("/");
			response.addCookie(accountCookie);
			Cookie companyCookie = new Cookie(Constants.CompanyId, accountId);
			companyCookie.setMaxAge(-1);
			companyCookie.setPath("/");
			response.addCookie(companyCookie);

			return new ModelAndView("index", "message", account);
		} else {
			return new ModelAndView("login", "message", "您输入的账号或者密码错误");
		}
	}
}
