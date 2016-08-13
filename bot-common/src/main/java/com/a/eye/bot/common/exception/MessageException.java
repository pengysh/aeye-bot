package com.a.eye.bot.common.exception;

/**
 * @Title: AppcationException.java
 * @author: pengysh
 * @date 2016年8月10日 下午7:52:31
 * @Description:统一异常处理类
 */
public class MessageException extends Exception {
	private static final long serialVersionUID = -1539403128584804554L;

	public MessageException(String message) {
		super(message);
	}
}
