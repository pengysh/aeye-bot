package com.a.eye.bot.user.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.common.ui.base.ControllerBase;
import com.a.eye.bot.common.ui.util.CookieUtil;
import com.a.eye.bot.user.share.dubbo.provider.IUserServiceProvider;
import com.a.eye.bot.user.share.entity.UserLoginEntity;
import com.a.eye.bot.user.ui.model.UserLoginModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/user", produces = { "application/json;charset=UTF-8" })
@Api(value = "/user", description = "用户服务")
public class UserController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(UserController.class.getName());

	@Reference
	private IUserServiceProvider userService;

	@RequestMapping(value = "userLogin", method = RequestMethod.POST)
	@ApiOperation(notes = "userLogin", httpMethod = "POST", value = "用户登录")
	@ResponseBody
	public void userLogin(Model model, @ModelAttribute UserLoginModel userLoginModel, HttpServletResponse response) throws IOException {
		logger.debug(userLoginModel.getUserCode() + "\t" + userLoginModel.getPassword());
		UserLoginEntity userLoginEntity = userService.userLogin(userLoginModel.getUserCode(), userLoginModel.getPassword());

		if (ObjectUtils.isEmpty(userLoginEntity)) {
			reply("用户名或者密码错误", response);
		} else {
			logger.debug(userLoginEntity.getUserId() + "\t" + userLoginEntity.getUserName() + "\t" + userLoginEntity.getCompanyId());
			CookieUtil.setCookie(response, String.valueOf(userLoginEntity.getUserId()), userLoginEntity.getUserName(), String.valueOf(userLoginEntity.getCompanyId()));
			reply("Success", response);
		}
	}

	@RequestMapping(value = "emailCheck", method = RequestMethod.POST)
	@ApiOperation(notes = "emailCheck", httpMethod = "POST", value = "邮箱是否已注册检查")
	@ResponseBody
	public void emailCheck(Model model, @ModelAttribute String email, HttpServletResponse response) throws IOException {
		logger.debug(email);
		boolean isRegister = userService.emailCheck(email);
		JsonObject resultJson = new JsonObject();
		resultJson.addProperty("isRegister", isRegister);
		reply(resultJson.toString(), response);
	}
}
