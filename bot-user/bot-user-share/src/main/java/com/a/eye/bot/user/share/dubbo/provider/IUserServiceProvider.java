package com.a.eye.bot.user.share.dubbo.provider;

import com.a.eye.bot.common.exception.MessageException;
import com.a.eye.bot.user.share.entity.UserLoginEntity;

/**
 * @Title: IUserServiceProvider.java
 * @author: pengysh
 * @date 2016年8月9日 下午1:47:29
 * @Description:用户服务
 */
public interface IUserServiceProvider {

	/**
	 * @Title: userLogin
	 * @author: pengysh
	 * @date 2016年8月10日 下午8:01:17
	 * @Description: 用户登录服务
	 * @param authCode
	 * @param password
	 * @return
	 */
	public UserLoginEntity userLogin(String authCode, String password);

	/**
	 * @Title: emailCheck
	 * @author: pengysh
	 * @date 2016年8月9日 下午2:23:01
	 * @Description:邮箱是否已注册检查
	 * @param email
	 * @return
	 * @throws MessageException
	 */
	public boolean emailCheck(String email);

	/**
	 * @Title: getUserDate
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:48:23
	 * @Description:查询单个用户资料
	 * @param userId
	 */
	public String getUserDate(Long userId);

	/**
	 * @Title: getBatchUserData
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:54:36
	 * @Description:查询多个用户资料
	 * @param userIdJsonArray
	 * @return
	 */
	public String getBatchUserData(String userIdJsonArray);
}
