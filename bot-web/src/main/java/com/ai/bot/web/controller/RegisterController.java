package com.ai.bot.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.bot.web.service.entity.Account;
import com.ai.bot.web.service.logic.AccountLogic;

@Controller
public class RegisterController {
	
	private Logger logger = LogManager.getLogger(this.getClass());

	private AccountLogic accountLogic;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String accountId = request.getParameter("accountId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Account account = new Account();
		account.setAccountId(accountId);
		account.setName(name);
		account.setEmail(email);
		account.setPassword(password);
		
		String responseStr = accountLogic.registerAccount(account);
		logger.debug(responseStr);
		if ("success".equals(responseStr)) {
			response.sendRedirect("login.jsp");
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			response.getWriter().write(responseStr);
			dispatcher.forward(request, response);
		}
	}
}
