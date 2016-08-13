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
import com.a.eye.bot.common.consts.Constants;
import com.a.eye.bot.common.exception.MessageException;

/**
 * @Title: UserAuthDataService.java
 * @author: pengysh
 * @date 2016年8月9日 上午1:05:17
 * @Description:用户认证数据操作服务
 */
@Service
@Transactional
public class UserAuthDataService {

	private static Logger logger = LogManager.getLogger(UserAuthDataService.class.getName());

	@Autowired
	private UserAuthMapper userAuthDao;

	@Autowired
	private UserAuthService userAuthService;

	/**
	 * @Title: addUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:05:34
	 * @Description:新增用户认证数据
	 * @param userId
	 * @param authCode
	 * @param password
	 * @return
	 * @throws MessageException
	 */
	public void addUserAuthData(Long userId, String authCode, String password) throws MessageException {
		logger.debug("新增用户认证数据：" + userId + "\t" + authCode + "\t" + password);
		boolean checkResult = userAuthService.checkAuthCode(authCode);
		if (checkResult) {
			logger.debug("创建用户认证数据");
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(userId);
			userAuth.setAuthCode(authCode);
			userAuth.setPassword(password);
			userAuth.setState(Constants.State_Active);
			userAuthDao.insert(userAuth);
		} else {
			logger.debug("系统已有用户认证数据，直接返回");
			throw new MessageException("系统已有此用户认证数据");
		}
	}

	/**
	 * @Title: deleteUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:05:49
	 * @Description:作废用户认证数据
	 * @param userId
	 * @param authCode
	 * @return
	 * @throws MessageException
	 */
	public void deleteUserAuthData(Long userId, String authCode) throws MessageException {
		logger.debug("作废用户认证数据：" + userId + "\t" + authCode);
		List<UserAuth> uesrAuthList = userAuthDao.selectByUserIdAndAuthCode(userId, authCode);
		if (!CollectionUtils.isEmpty(uesrAuthList)) {
			UserAuth userAuth = uesrAuthList.get(0);
			userAuth.setState(Constants.State_Delete);
			userAuthDao.updateByPrimaryKey(userAuth);
		} else {
			throw new MessageException("系统无此用户认证数据");
		}
	}

	/**
	 * @Title: modifyUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:06:01
	 * @Description: 变更用户密码
	 * @param userId
	 * @param authCode
	 * @param password
	 * @return
	 * @throws MessageException
	 */
	public void modifyUserAuthData(Long userId, String authCode, String password) throws MessageException {
		logger.debug("修改密码：" + userId + "\t" + authCode);
		List<UserAuth> uesrAuthList = userAuthDao.selectByUserIdAndAuthCode(userId, authCode);
		if (!CollectionUtils.isEmpty(uesrAuthList)) {
			UserAuth userAuth = uesrAuthList.get(0);
			userAuth.setPassword(password);
			userAuthDao.updateByPrimaryKey(userAuth);
		} else {
			throw new MessageException("系统无此用户认证数据");
		}
	}
}
