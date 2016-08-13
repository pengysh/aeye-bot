package com.a.eye.bot.user.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.a.eye.bot.user.cache.dao.UserMapper;
import com.a.eye.bot.user.cache.entity.User;
import com.a.eye.bot.user.share.entity.UserInfo;
import com.a.eye.bot.user.share.redis.UserInfoJedisRepository;

/**
 * @Title: UserCache.java
 * @author: pengysh
 * @date 2016年8月13日 下午5:43:24
 * @Description:用户信息缓存初始化
 */
public class UserCache {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	public void load() {
		Integer start = 1;
		Integer pageNow = 1;
		Integer pageSize = 2;

		while (true) {
			start = (pageNow - 1) * pageSize;
			List<User> userList = userMapper.selectOnPage(start, pageSize);
			pageNow++;
			if (CollectionUtils.isEmpty(userList)) {
				break;
			} else {
				for (User user : userList) {
					UserInfo uesrInfo = new UserInfo();
					uesrInfo.setId(user.getId());
					uesrInfo.setCompanyId(user.getCompanyId());
					uesrInfo.setEmail(user.getEmail());
					uesrInfo.setName(user.getName());
					uesrInfo.setHeadImage(user.getHeadImage());

					userInfoJedisRepository.saveUserInfo(uesrInfo);
				}
			}
		}
	}
}
