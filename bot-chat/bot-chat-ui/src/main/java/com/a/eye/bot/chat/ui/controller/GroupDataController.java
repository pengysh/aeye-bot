package com.a.eye.bot.chat.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a.eye.bot.chat.share.dubbo.provider.IGroupDataServiceProvider;
import com.a.eye.bot.chat.ui.model.GroupModel;
import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.ui.base.ControllerBase;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.JsonArray;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/group", produces = { "application/json;charset=UTF-8" })
@Api(value = "/group", description = "群组服务")
public class GroupDataController extends ControllerBase {

	private static Logger logger = LogManager.getLogger(GroupDataController.class.getName());

	@Reference
	private IGroupDataServiceProvider groupDataServiceProvider;

	@RequestMapping(value = "createChatGroup", method = RequestMethod.POST)
	@ApiOperation(notes = "createChatGroup", httpMethod = "POST", value = "用户群组列表")
	@ResponseBody
	public void createChatGroup(Model model, @CookieValue("userId") String userId, @ModelAttribute GroupModel groupModel, HttpServletResponse response) throws IOException {
		logger.debug("创建群组列表：" + groupModel.getTitle() + "\t" + groupModel.getPurpose() + "\t" + groupModel.getPublicOrPrivate() + "\t" + groupModel.getUserJson());
		String userJson = groupModel.getUserJson();
		String[] groupMembers = userJson.split(",");
		JsonArray userJsonArray = new JsonArray();
		userJsonArray.add(Long.valueOf(userId));
		for (String groupMember : groupMembers) {
			if (StringUtils.isNotBlank(groupMember)) {
				userJsonArray.add(Long.valueOf(groupMember));
			}
		}
		groupDataServiceProvider.createChatGroup(groupModel.getTitle(), groupModel.getPurpose(), groupModel.getPublicOrPrivate(), userJsonArray.toString());
		reply(Constants.EmptyString, response);
	}
}
