package com.a.eye.bot.chat.ui.controller;

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

import com.a.eye.bot.chat.share.dubbo.provider.IUserFriendsDataServiceProvider;
import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/user/friendsdata")
@Api(value = "/user/friendsdata", description = "用户好友数据服务")
public class UserFriendsDataController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(UserFriendsDataController.class.getName());

	@Reference
	private IUserFriendsDataServiceProvider userFriendsDataServiceProvider;

	@RequestMapping(value = "addFriend", method = RequestMethod.POST)
	@ApiOperation(notes = "addFriend", httpMethod = "POST", value = "添加好友")
	@ResponseBody
	public void addFriend(@ApiParam(required = true, value = "userFriends data") @CookieValue("userId") String userId, @ModelAttribute("friendsUserId") String friendsUserId,
			HttpServletResponse response) throws IOException {
		logger.debug("addFriend" + "\t" + userId + "\t" + friendsUserId);
		userFriendsDataServiceProvider.addFriend(Long.valueOf(userId), Long.valueOf(friendsUserId));
		reply(Constants.EmptyString, response);
	}

	@RequestMapping(value = "removeFriend", method = RequestMethod.POST)
	@ApiOperation(notes = "removeFriend", httpMethod = "POST", value = "移除好友")
	@ResponseBody
	public void removeFriend(@ApiParam(required = true, value = "userFriends data") @CookieValue("userId") String userId, @ModelAttribute("friendsUserId") String friendsUserId,
			HttpServletResponse response) throws IOException {
		logger.debug("removeFriend" + "\t" + userId + "\t" + friendsUserId);
		userFriendsDataServiceProvider.removeFriend(Long.valueOf(userId), Long.valueOf(friendsUserId));
		reply(Constants.EmptyString, response);
	}
}
