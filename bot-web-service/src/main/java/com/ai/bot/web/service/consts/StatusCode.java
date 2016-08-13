package com.ai.bot.web.service.consts;

/**
 * 
 * @Title: StatusCode.java
 * @author: pengysh
 * @date 2016年8月8日 下午9:42:49
 * @Description:状态码常量
 */
public class StatusCode {
	// 业务处理成功
	public static final int GLOBAL_SUCCESS = 1;

	// 业务处理失败
	public static final int GLOBAL_FAIL = 0;

	// session过期
	public static final int SESSION_TIMEOUT = -2;

	// 不是有效的json格式
	public static final int STATUS_INVALID_JSON = 1001;

	// 不是有效的参数
	public static final int STATUS_INVALID_PARAMS = 1002;

	// 文件删除失败
	public static final int STATUS_FILE_DELETE_FAIL = 1003;

	// 数据库记录不存在
	public static final int STATUS_NOT_EXIST = 1004;

	// 权限不足
	public static final int STATUS_LACK_AUTHORITY = 1005;

	// 文件操作失败
	public static final int STATUS_FILE_ERROR = 1006;

	// 数据库记录已存在
	public static final int STATUS_DB_EXIST = 1007;
}
