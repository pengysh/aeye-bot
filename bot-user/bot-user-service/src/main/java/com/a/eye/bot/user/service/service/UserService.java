package com.a.eye.bot.user.service.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.a.eye.bot.common.cache.redis.UserInfoJedisRepository;
import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.a.eye.bot.common.exception.MessageException;
import com.a.eye.bot.user.service.dao.UserMapper;
import com.a.eye.bot.user.service.dubbo.consumer.UserAuthServiceConsumer;
import com.a.eye.bot.user.service.entity.User;
import com.a.eye.bot.user.service.util.UserInfoParseUtil;
import com.a.eye.bot.user.share.entity.UserLoginEntity;

/**
 * @Title: UserService.java
 * @author: pengysh
 * @date 2016年8月9日 下午3:06:09
 * @Description:用户服务
 */
@Service
@Transactional
public class UserService {

	private static Logger logger = LogManager.getLogger(UserService.class.getName());

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserAuthServiceConsumer userAuthService;

	@Autowired
	private UserInfoJedisRepository userInfoJedisRepository;

	/**
	 * @Title: userLogin
	 * @author: pengysh
	 * @date 2016年8月9日 下午3:06:31
	 * @Description:用户登录服务
	 * @param authCode
	 * @param password
	 * @return
	 * @throws MessageException
	 */
	public UserLoginEntity userLogin(String authCode, String password) {
		Long uesrId = userAuthService.authUser(authCode, password);
		logger.debug("用户登录：" + authCode + "\t" + password + "\t" + uesrId);
		if (uesrId == -1l) {
			logger.debug("登录失败");
			return null;
		} else {
			logger.debug("登录成功");
			User uesr = userMapper.selectByPrimaryKey(uesrId);

			UserLoginEntity userLoginEntity = new UserLoginEntity();
			userLoginEntity.setUserId(uesr.getId());
			userLoginEntity.setUserName(uesr.getName());
			userLoginEntity.setCompanyId(uesr.getCompanyId());
			return userLoginEntity;
		}
	}

	/**
	 * @Title: emailCheck
	 * @author: pengysh
	 * @date 2016年8月9日 下午3:06:45
	 * @Description:邮箱是否已注册检查
	 * @param email
	 * @return
	 * @throws MessageException
	 */
	public boolean emailCheck(String email) {
		logger.debug("检查邮箱是否已注册：" + email);
		List<User> userList = userMapper.selectByEmail(email);
		if (!CollectionUtils.isEmpty(userList)) {
			logger.debug("已注册：" + email);
			return false;
		} else {
			logger.debug("未注册：" + email);
			return true;
		}
	}

	/**
	 * @Title: getUserDate
	 * @author: pengysh
	 * @date 2016年8月13日 下午4:16:26
	 * @Description:获取单个用户信息
	 * @param userId
	 * @return
	 */
	public UserCacheInfo getUserDate(Long userId) {
		UserCacheInfo userCacheInfo = userInfoJedisRepository.selectUserInfo(userId);
		if (ObjectUtils.isEmpty(userCacheInfo)) {
			User user = userMapper.selectByPrimaryKey(userId);

			if (!ObjectUtils.isEmpty(user)) {
				// 插入缓存
				userCacheInfo = UserInfoParseUtil.parse(user);
				userInfoJedisRepository.saveUserInfo(userCacheInfo);
			}
		}
		return userCacheInfo;
	}

	/**
	 * @Title: getBatchUserData
	 * @author: pengysh
	 * @date 2016年8月13日 下午4:16:38
	 * @Description:获取多个用户信息
	 * @param userIdJsonArrayStr
	 * @return
	 */
	public List<UserCacheInfo> getBatchUserData(Long[] userIds) {
		List<UserCacheInfo> userList = new ArrayList<UserCacheInfo>();

		for (Long userId : userIds) {
			UserCacheInfo userCacheInfo = userInfoJedisRepository.selectUserInfo(userId);
			if (ObjectUtils.isEmpty(userCacheInfo)) {
				User user = userMapper.selectByPrimaryKey(userId);

				if (!ObjectUtils.isEmpty(user)) {
					// 插入缓存
					userCacheInfo = UserInfoParseUtil.parse(user);
					userInfoJedisRepository.saveUserInfo(userCacheInfo);
				}
			}
			userList.add(userCacheInfo);
		}

		return userList;
	}
}
