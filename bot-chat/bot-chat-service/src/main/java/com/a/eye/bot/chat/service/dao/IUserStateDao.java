package com.a.eye.bot.chat.service.dao;

/**
 * @Title: IUserStateDao.java
 * @author: pengysh
 * @date 2016年8月12日 下午9:20:05
 * @Description:用户状态
 */
public interface IUserStateDao {

	public void saveUserState(Long userId, String state);

	public void updateUserState(Long userId, String state);

	public String selectUserState(Long userId);
}
