package com.a.eye.bot.common.cache.user;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.dao.UserMapper;
import com.a.eye.bot.common.cache.user.entity.User;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;

/**
 * @Title: UserCache.java
 * @author: pengysh
 * @date 2016年8月13日 下午5:43:24
 * @Description:用户信息缓存初始化
 */
public class UserCache {
	
	private static Logger logger = LogManager.getLogger(UserCache.class.getName());

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	public void load() {
		logger.debug("开始加载用户缓存");
		Integer start = 1;
		Integer pageNow = 1;
		Integer pageSize = 50;

		while (true) {
			start = (pageNow - 1) * pageSize;
			List<User> userList = userMapper.selectOnPage(start, pageSize);
			pageNow++;
			if (CollectionUtils.isEmpty(userList)) {
				break;
			} else {
				for (User user : userList) {
					UserCacheInfo uesrInfo = new UserCacheInfo();
					uesrInfo.setUserId(user.getId());
					uesrInfo.setEmail(user.getEmail());
					uesrInfo.setName(user.getName());
					uesrInfo.setHeadImage(user.getHeadImage());

					userInfoJedisRepository.saveUserInfo(uesrInfo);
				}
			}
		}
		logger.debug("结束加载用户缓存");
	}
}
