package com.a.eye.bot.auth.service.service;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.a.eye.bot.auth.service.dao.UserAuthMapper;
import com.a.eye.bot.auth.service.entity.UserAuth;
import com.a.eye.bot.common.exception.MessageException;

/**
 * @Title: UserAuthService.java
 * @author: pengysh
 * @date 2016年8月8日 下午11:18:42
 * @Description:用户认证服务
 */
@Service
@Transactional
public class UserAuthService {

	private static Logger logger = LogManager.getLogger(UserAuthService.class.getName());

	@Autowired
	private UserAuthMapper userAuthDao;

	/**
	 * @Title: authUser
	 * @author: pengysh
	 * @date 2016年8月9日 上午12:30:18
	 * @Description:根据密码验证
	 * @param authCode
	 * @param password
	 * @return
	 * @throws MessageException
	 */
	public Long authUser(String authCode, String password) {
		logger.debug("用户认证：" + authCode);
		List<UserAuth> userAuthList = userAuthDao.selectByAuthCode(authCode);
		if (!CollectionUtils.isEmpty(userAuthList)) {
			UserAuth userAuth = userAuthList.get(0);
			if (userAuth.getPassword().equals(password)) {
				logger.debug("认证通过：" + authCode);
				return userAuth.getUserId();
			} else {
				logger.debug("认证不通过：" + authCode);
				return -1l;
			}
		}
		return null;
	}

	/**
	 * @Title: authUser
	 * @author: pengysh
	 * @date 2016年8月9日 上午12:29:56
	 * @Description: 根据令牌认证
	 * @param authToken
	 * @return
	 */
	public Long authUser(String authToken) {
		return -1l;
	}

	/**
	 * @Title: checkAuthCode
	 * @author: pengysh
	 * @date 2016年8月10日 下午12:06:08
	 * @Description:检查认证账号是否已存在
	 * @param authCode
	 * @return
	 */
	public boolean checkAuthCode(String authCode) {
		logger.debug("检查认证账号是否存在：" + authCode);
		List<UserAuth> userAuthList = userAuthDao.selectByAuthCode(authCode);
		if (CollectionUtils.isEmpty(userAuthList)) {
			logger.debug(authCode + "\t" + "不存在");
			return true;
		} else {
			logger.debug(authCode + "\t" + "存在");
			return false;
		}
	}
}
