package com.a.eye.bot.auth.share.dubbo.provider;

import com.a.eye.bot.common.exception.MessageException;

/**
 * @Title: IUserAuthDataServiceProvider.java
 * @author: pengysh
 * @date 2016年8月9日 上午12:57:35
 * @Description:用户认证数据操作服务
 */
public interface IUserAuthDataServiceProvider {

	/**
	 * @Title: addUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:00:43
	 * @Description:新增用户认证数据
	 * @param userId
	 * @param authCode
	 * @param password
	 * @return
	 */
	public void addUserAuthData(Long userId, String authCode, String password) throws MessageException;

	/**
	 * @Title: deleteUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:02:43
	 * @Description:作废用户认证数据
	 * @param userId
	 * @param authCode
	 * @return
	 */
	public void deleteUserAuthData(Long userId, String authCode) throws MessageException;

	/**
	 * @Title: modifyUserAuthData
	 * @author: pengysh
	 * @date 2016年8月9日 上午1:03:49
	 * @Description:变更用户密码
	 * @param userId
	 * @param authCode
	 * @param password
	 * @return
	 */
	public void modifyUserAuthData(Long userId, String authCode, String password) throws MessageException;

}
