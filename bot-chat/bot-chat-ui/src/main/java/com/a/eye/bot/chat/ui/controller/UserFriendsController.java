package com.a.eye.bot.chat.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.chat.share.dubbo.provider.IUserFriendsServiceProvider;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/friend", produces = { "application/json;charset=UTF-8" })
@Api(value = "/friend", description = "用户好友服务")
public class UserFriendsController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(UserFriendsController.class.getName());

	@Reference
	private IUserFriendsServiceProvider userFriendsServiceProvider;

	@RequestMapping(value = "getUserFriends", method = RequestMethod.POST)
	@ApiOperation(notes = "getUserFriends", httpMethod = "POST", value = "用户好友列表")
	@ResponseBody
	public void getUserFriends(@ApiParam(required = true, value = "userFriends data") @CookieValue("userId") String userId, HttpServletResponse response) throws IOException {
		logger.debug("获取用户好友列表：" + userId);
		String friends = userFriendsServiceProvider.getUserFriends(Long.valueOf(userId));
		logger.debug("获取到的用户好友列表：" + friends);
		reply(friends, response);
	}
}
