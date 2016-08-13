package com.ai.bot.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title: CookieUtil.java
 * @author: pengysh
 * @date 2016年8月8日 下午9:47:48
 * @Description:Cookie工具类
 */
public class CookieUtil {

	/**
	 * 
	 * @Title: setCookie
	 * @author: pengysh
	 * @date 2016年8月8日 下午9:55:38
	 * @Description: 登录后设置Cookie信息
	 * @param response
	 * @param userId
	 * @param userName
	 * @param companyId
	 */
	public static void setCookie(HttpServletResponse response, String userId, String userName, String companyId) {
		Cookie userIdCookie = new Cookie("userId", userId);
		userIdCookie.setMaxAge(-1);
		userIdCookie.setPath("/");
		response.addCookie(userIdCookie);

		Cookie userNameCookie = new Cookie("userName", userName);
		userNameCookie.setMaxAge(-1);
		userNameCookie.setPath("/");
		response.addCookie(userNameCookie);

		Cookie companyIdCookie = new Cookie("companyId", companyId);
		companyIdCookie.setMaxAge(-1);
		companyIdCookie.setPath("/");
		response.addCookie(companyIdCookie);
	}

	/**
	 * 
	 * @Title: getUserId
	 * @author: pengysh
	 * @date 2016年8月8日 下午10:00:37
	 * @Description:从Cookie中获取userId
	 * @param request
	 * @return
	 */
	public static Long getUserId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("userId".equals(cookie.getName())) {
				return Long.parseLong(cookie.getValue());
			}
		}
		return -1l;
	}
}
