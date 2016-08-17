package com.a.eye.bot.user.service.util;

import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.user.share.entity.DepartUserInfo;

/**
 * @Title: DepartUserInfoParseUtil.java
 * @author: pengysh
 * @date 2016年8月13日 下午6:18:01
 * @Description:用户存储层对象转换为展现层对象
 */
public class DepartUserInfoParseUtil {

	public static DepartUserInfo parse(UserCacheInfo userInfo) {
		DepartUserInfo departUserInfo = new DepartUserInfo();
		departUserInfo.setId(userInfo.getUserId());
		departUserInfo.setEmail(userInfo.getEmail());
		departUserInfo.setName(userInfo.getName());
		departUserInfo.setHeadImage(userInfo.getHeadImage());
		return departUserInfo;
	}
}
