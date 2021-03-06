package com.a.eye.bot.common.ui.util;

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
	 * @Title: setCookie
	 * @author: pengysh
	 * @date 2016年8月8日 下午9:55:38
	 * @Description: 登录后设置Cookie信息
	 * @param response
	 * @param userId
	 * @param userName
	 * @param companyId
	 */
	public static void setCookie(HttpServletResponse response, String userId, String companyId) {
		Cookie userIdCookie = new Cookie("userId", userId);
		userIdCookie.setMaxAge(-1);
		userIdCookie.setPath("/");
		response.addCookie(userIdCookie);

		Cookie companyIdCookie = new Cookie("companyId", companyId);
		companyIdCookie.setMaxAge(-1);
		companyIdCookie.setPath("/");
		response.addCookie(companyIdCookie);
	}

	/**
	 * @Title: removeCookie
	 * @author: pengysh
	 * @date 2016年8月14日 上午6:02:04
	 * @Description:退出时清除Cookie信息
	 * @param response
	 * @param userId
	 */
	public static void removeCookie(HttpServletResponse response, String userId) {
		Cookie userIdCookie = new Cookie("userId", null);
		userIdCookie.setMaxAge(0);
		userIdCookie.setPath("/");
		response.addCookie(userIdCookie);

		Cookie companyIdCookie = new Cookie("companyId", null);
		companyIdCookie.setMaxAge(0);
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

	/**
	 * @Title: checkLogin
	 * @author: pengysh
	 * @date 2016年8月16日 上午12:58:40
	 * @Description:判断用户是否登录
	 * @param request
	 * @return
	 */
	public static boolean checkLogin(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userId".equals(cookie.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
