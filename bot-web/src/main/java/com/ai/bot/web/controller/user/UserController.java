package com.ai.bot.web.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.bot.web.service.consts.StatusCode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 
 * @Title: UserController.java
 * @author: pengysh
 * @date 2016年8月8日 下午9:26:54
 * @Description:
 */
public class UserController {

	private Gson gson = new Gson();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute UserVo vo, HttpSession session) {
		// 组装查询参数
		JsonObject params = new JsonObject();
		params.put("username", vo.getUsername());
		params.put("password", vo.getPassword());

		// 检查登录
		String result = userService.checkLogin(params.toString());

		// 解析检查结果
		JsonObject resultObj = gson.fromJson(result, JsonObject.class);

		// 登录失败
		if (resultObj.get("status").getAsInt() != StatusCode.GLOBAL_SUCCESS) {
			model.addAttribute("error", true);
			return "home";
		}

		// 登录成功，设置session信息
		SessionUtil.setSession(session, resultObj.getJSONObject("user"));

		// 跳转到主页
		return "main/main";
	}
}
