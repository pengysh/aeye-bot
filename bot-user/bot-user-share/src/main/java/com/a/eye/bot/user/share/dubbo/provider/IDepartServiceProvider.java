package com.a.eye.bot.user.share.dubbo.provider;

/**
 * @Title: IDepartServiceProvider.java
 * @author: pengysh
 * @date 2016年8月14日 上午1:24:51
 * @Description:部门服务
 */
public interface IDepartServiceProvider {

	public String getUserInDept(Long deaprtId, Long userId);
}
