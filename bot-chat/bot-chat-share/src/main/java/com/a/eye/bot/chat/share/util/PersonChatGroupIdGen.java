package com.a.eye.bot.chat.share.util;

/**
 * @Title: PersonChatGroupIdGen.java
 * @author: pengysh
 * @date 2016年8月15日 下午12:04:04
 * @Description:点对点对话时模拟生成GROUPID
 */
public class PersonChatGroupIdGen {

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

	/**
	 * @Title: getTogetherUserId
	 * @author: pengysh
	 * @date 2016年8月17日 下午4:55:13
	 * @Description:获取对方的UserId
	 * @param genId
	 * @param userIdOne
	 * @return
	 */
	public static Long getTogetherUserId(String genId, Long userIdOne) {
		String[] userIds = genId.split("-");
		if (userIds[0].equals(userIdOne)) {
			return Long.valueOf(userIds[1]);
		} else {
			return Long.valueOf(userIds[0]);
		}
	}
}
