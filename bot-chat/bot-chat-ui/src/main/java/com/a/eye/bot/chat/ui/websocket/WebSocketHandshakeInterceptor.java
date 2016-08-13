package com.a.eye.bot.chat.ui.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.a.eye.bot.common.ui.consts.Constants;
import com.a.eye.bot.common.ui.util.CookieUtil;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest servletRequest = (HttpServletRequest) request;

			Long userId = CookieUtil.getUserId(servletRequest);

			logger.debug("cookie中获取的用户ID：" + userId);
			attributes.put(Constants.UserId, userId);
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}

}
