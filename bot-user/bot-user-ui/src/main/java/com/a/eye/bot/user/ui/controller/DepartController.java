package com.a.eye.bot.user.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.common.ui.base.ControllerBase;
import com.a.eye.bot.user.share.dubbo.provider.IDepartServiceProvider;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/user/depart", produces = { "application/x-www-form-urlencoded" })
@Api(value = "/user/depart", description = "部门服务")
public class DepartController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(DepartController.class.getName());

	@Reference
	private IDepartServiceProvider departServiceProvider;

	@RequestMapping(value = "getUserInDept", method = RequestMethod.POST)
	@ApiOperation(notes = "getUserInDept", httpMethod = "POST", value = "查找部门中的员工")
	@ResponseBody
	public void getUserInDept(@ApiParam(required = true, value = "userFriends data") @CookieValue("userId") String userId, @ModelAttribute("deaprtId") String deaprtId,
			HttpServletResponse response) throws IOException {
		logger.debug("查找部门中的员工：" + userId + "\t" + deaprtId);
		String userInDept = departServiceProvider.getUserInDept(Long.valueOf(deaprtId), Long.valueOf(userId));
		logger.debug("部门中的员工：" + userInDept);
		reply(userInDept, response);
	}
}
