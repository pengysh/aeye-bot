package com.a.eye.bot.user.service.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.a.eye.bot.user.service.dao.DepartMapper;
import com.a.eye.bot.user.service.entity.Depart;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * @Title: CompanyService.java
 * @author: pengysh
 * @date 2016年8月14日 上午12:53:16
 * @Description:部门数据服务
 */
@Service
@Transactional
public class DepartDataService {

	private static Logger logger = LogManager.getLogger(DepartDataService.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private DepartMapper departMapper;

	/**
	 * @Title: addUserInDept
	 * @author: pengysh
	 * @date 2016年8月14日 上午5:09:39
	 * @Description:添加员工到部门
	 * @param deaprtId
	 * @param userId
	 */
	public void addUserInDept(Long deaprtId, Long userId) {
		logger.debug("添加员工到部门：" + deaprtId + "\t" + userId);
		Depart depart = departMapper.selectByPrimaryKey(deaprtId);
		String userIds = depart.getUsers();
		if (StringUtils.isEmpty(userIds)) {
			JsonArray userIdJson = new JsonArray();
			userIdJson.add(userId);
			depart.setUsers(userIdJson.toString());
			departMapper.updateByPrimaryKeySelective(depart);
		} else {
			JsonArray userIdJson = gson.fromJson(userIds, JsonArray.class);
			userIdJson.add(userId);
			depart.setUsers(userIdJson.toString());
			departMapper.updateByPrimaryKeySelective(depart);
		}
	}
}
