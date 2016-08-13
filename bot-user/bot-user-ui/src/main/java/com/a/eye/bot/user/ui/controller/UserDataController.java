package com.a.eye.bot.user.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.common.exception.MessageException;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.a.eye.bot.user.share.dubbo.provider.IUserDataServiceProvider;
import com.a.eye.bot.user.ui.model.UserRegisterModel;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/userdata")
@Api(value = "/userdata", description = "用户数据服务")
public class UserDataController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(UserDataController.class.getName());

	@Reference
	private IUserDataServiceProvider userDataService;

	@RequestMapping(value = "userRegister", method = RequestMethod.POST)
	@ApiOperation(notes = "userRegister", httpMethod = "POST", value = "用户注册服务")
	@ResponseBody
	public void userRegister(Model model, @ModelAttribute UserRegisterModel userRegisterModel, HttpServletResponse response) throws IOException {
		logger.debug(userRegisterModel.getName() + "\t" + userRegisterModel.getEmail() + "\t" + userRegisterModel.getPassword());
		try {
			userDataService.addUserData(userRegisterModel.getName(), userRegisterModel.getEmail(), userRegisterModel.getPassword());
		} catch (MessageException e) {
			logger.debug(e.getMessage());
			reply(e.getMessage(), response);
			return;
		}
		reply("Success", response);
	}
}
