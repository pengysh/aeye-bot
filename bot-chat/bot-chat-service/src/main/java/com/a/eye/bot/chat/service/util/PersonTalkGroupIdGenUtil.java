package com.a.eye.bot.chat.service.util;

/**
 * @Title: PersonTalkGroupIdGenUtil.java
 * @author: pengysh
 * @date 2016年8月15日 下午12:04:04
 * @Description:点对点对话时模拟生成GROUPID
 */
public class PersonTalkGroupIdGenUtil {

	/**
	 * @Title: gen
	 * @author: pengysh
	 * @date 2016年8月15日 下午12:04:43
	 * @Description:编号小的永远放前面
	 * @param userIdOne
	 * @param userIdTwo
	 * @return
	 */
	public static String gen(Long userIdOne, Long userIdTwo) {
		if (userIdOne > userIdTwo) {
			return String.valueOf(userIdTwo) + "-" + String.valueOf(userIdOne);
		} else {
			return String.valueOf(userIdOne) + "-" + String.valueOf(userIdTwo);
		}
	}
}
