package com.a.eye.bot.user.service.dubbo.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.a.eye.bot.user.service.service.DepartService;
import com.a.eye.bot.user.share.dubbo.provider.IDepartServiceProvider;
import com.a.eye.bot.user.share.entity.DepartUserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;

@Service
public class DepartServiceProvider implements IDepartServiceProvider {

	private Gson gson = new Gson();

	@Autowired
	private DepartService departService;

	@Override
	public String getUserInDept(Long deaprtId, Long userId) {
		List<DepartUserInfo> departUserInfoList = departService.getUserInDept(deaprtId, userId);
		return gson.toJson(departUserInfoList);
	}

}
