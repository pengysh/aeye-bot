package com.a.eye.bot.auth.share.dubbo.provider;

/**
 * 
 * @Title: IUserAuthServiceProvider.java
 * @author: pengysh
 * @date 2016年8月8日 下午11:01:40
 * @Description:用户认证服务
 */
public interface IUserAuthServiceProvider {

	/**
	 * 
	 * @Title: authUser
	 * @author: pengysh
	 * @date 2016年8月9日 上午12:28:33
	 * @Description: 根据密码验证
	 * @param authCode
	 * @param password
	 * @return
	 */
	public Long authUser(String authCode, String password);

	/**
	 * 
	 * @Title: authUser
	 * @author: pengysh
	 * @date 2016年8月9日 上午12:28:44
	 * @Description: 根据令牌认证
	 * @param authToken
	 * @return
	 */
	public Long authUser(String authToken);

	/**
	 * @Title: checkAuthCode
	 * @author: pengysh
	 * @date 2016年8月10日 下午12:05:32
	 * @Description:检查认证账号是否已存在
	 * @param authCode
	 * @return
	 */
	public boolean checkAuthCode(String authCode);
}
