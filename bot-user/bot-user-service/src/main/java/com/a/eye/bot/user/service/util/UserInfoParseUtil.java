package com.a.eye.bot.user.service.util;

import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.user.service.entity.User;

/**
 * @Title: UserInfoParseUtil.java
 * @author: pengysh
 * @date 2016年8月13日 下午6:18:01
 * @Description:用户存储层对象转换为展现层对象
 */
public class UserInfoParseUtil {

	public static UserCacheInfo parse(User user) {
		UserCacheInfo uesrInfo = new UserCacheInfo();
		uesrInfo.setUserId(user.getId());
		uesrInfo.setEmail(user.getEmail());
		uesrInfo.setName(user.getName());
		uesrInfo.setHeadImage(user.getHeadImage());
		return uesrInfo;
	}
}
