package com.a.eye.bot.todo.share.service;

/**
 * @Title: ITodoItemDataServiceConsumer.java
 * @author: pengysh
 * @date 2016年8月11日 下午3:48:52
 * @Description:待办事项数据操作服务
 */
public interface ITodoItemDataService {

	/**
	 * @Title: addTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午3:51:40
	 * @Description:创建待办事项
	 * @param userId
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param allDay
	 * @param importance
	 */
	public void addTodoItemData(Long userId, String title, Long startTime, Long endTime, Boolean allDay, String importance);

	/**
	 * @Title: modifyTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:14:27
	 * @Description:变更待办事项
	 * @param id
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param allDay
	 * @param importance
	 */
	public void modifyTodoItemData(Long id, String title, Long startTime, Long endTime, Boolean allDay, String importance);

	/**
	 * @Title: deleteTodoItemData
	 * @author: pengysh
	 * @date 2016年8月11日 下午4:19:57
	 * @Description:作废待办事项
	 * @param id
	 */
	public void deleteTodoItemData(Long id);
}
