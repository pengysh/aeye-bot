package com.a.eye.bot.user.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a.eye.bot.user.service.dao.DepartMapper;
import com.a.eye.bot.user.service.entity.Depart;
import com.a.eye.bot.user.service.util.DepartUserInfoParseUtil;
import com.a.eye.bot.user.share.entity.DepartUserInfo;
import com.a.eye.bot.user.share.entity.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * @Title: CompanyService.java
 * @author: pengysh
 * @date 2016年8月14日 上午12:53:16
 * @Description:部门服务
 */
@Service
@Transactional
public class DepartService {

	private static Logger logger = LogManager.getLogger(DepartService.class.getName());

	private Gson gson = new Gson();

	@Autowired
	private DepartMapper departMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserFriendsService userFriendsService;

	/**
	 * @Title: getUserInDept
	 * @author: pengysh
	 * @date 2016年8月14日 上午12:22:45
	 * @Description:查找部门中的员工
	 * @param deptId
	 * @return
	 */
	public List<DepartUserInfo> getUserInDept(Long deaprtId, Long userId) {
		logger.debug("查找部门中的员工：" + deaprtId + "\t" + userId);
		Depart depart = departMapper.selectByPrimaryKey(deaprtId);
		String userIds = depart.getUsers();
		JsonArray userIdJson = gson.fromJson(userIds, JsonArray.class);

		Map<Long, Long> userIdMap = userFriendsService.getUserFriendIds(userId);

		List<DepartUserInfo> departUserInfoList = new ArrayList<DepartUserInfo>();
		for (int i = 0; i < userIdJson.size(); i++) {
			Long userIdInDepart = userIdJson.get(i).getAsLong();
			UserInfo userInfo = userService.getUserDate(userIdInDepart);
			DepartUserInfo departUserInfo = DepartUserInfoParseUtil.parse(userInfo);
			if (userIdMap.containsKey(departUserInfo.getId())) {
				departUserInfo.setFriend(true);
			}
			if (userId != departUserInfo.getId()) {
				departUserInfoList.add(departUserInfo);
			}
		}

		return departUserInfoList;
	}
}
