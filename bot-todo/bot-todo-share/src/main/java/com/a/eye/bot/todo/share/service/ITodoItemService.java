package com.a.eye.bot.todo.share.service;

/**
 * @Title: ITodoItemServiceConsumer.java
 * @author: pengysh
 * @date 2016年8月11日 下午4:22:07
 * @Description:待办事项服务
 */
public interface ITodoItemService {

	/**
	 * @Title: getUserToDoItem
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:23:33
	 * @Description:获取指定人员的待办事项清单
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public String getUserToDoItem(Long userId, String start, String end);

	/**
	 * @Title: drogUserToDoItem
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:39:49
	 * @Description:拖动待办事项时变更待办事项的开始时间和结束时间
	 * @param id
	 * @param dayOffset
	 * @param hourOffset
	 * @param minuteOffset
	 */
	public void drogUserToDoItem(Long id, Integer dayOffset, Integer hourOffset, Integer minuteOffset);
}
