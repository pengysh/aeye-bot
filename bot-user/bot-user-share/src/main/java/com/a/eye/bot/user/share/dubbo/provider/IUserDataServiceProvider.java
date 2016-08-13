package com.a.eye.bot.user.share.dubbo.provider;

import com.a.eye.bot.common.exception.MessageException;

/**
 * @Title: IUserDataServiceProvider.java
 * @author: pengysh
 * @date 2016年8月9日 上午12:57:35
 * @Description:用户认证数据操作服务
 */
public interface IUserDataServiceProvider {

	/**
	 * @Title: addUserData
	 * @author: pengysh
	 * @date 2016年8月9日 下午2:35:25
	 * @Description:创建用户
	 * @param name
	 * @param email
	 * @param password
	 */
	public void addUserData(String name, String email, String password) throws MessageException;

}
