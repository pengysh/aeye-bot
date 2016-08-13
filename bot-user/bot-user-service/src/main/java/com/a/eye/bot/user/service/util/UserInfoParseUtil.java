package com.a.eye.bot.user.service.util;

import com.a.eye.bot.user.service.entity.User;
import com.a.eye.bot.user.share.entity.UserInfo;

/**
 * @Title: UserInfoParseUtil.java
 * @author: pengysh
 * @date 2016年8月13日 下午6:18:01
 * @Description:用户存储层对象转换为展现层对象
 */
public class UserInfoParseUtil {

	public static UserInfo parse(User user) {
		UserInfo uesrInfo = new UserInfo();
		uesrInfo.setId(user.getId());
		uesrInfo.setCompanyId(user.getCompanyId());
		uesrInfo.setEmail(user.getEmail());
		uesrInfo.setName(user.getName());
		uesrInfo.setHeadImage(user.getHeadImage());
		return uesrInfo;
	}
}
