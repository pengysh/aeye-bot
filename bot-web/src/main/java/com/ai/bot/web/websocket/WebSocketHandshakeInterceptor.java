package com.ai.bot.web.websocket;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.ai.bot.web.service.util.Constants;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

			Cookie[] cookies = servletRequest.getServletRequest().getCookies();
			String accountId = "";
			for (Cookie cookie : cookies) {
				if (Constants.AccountId.equals(cookie.getName())) {
					accountId = cookie.getValue();
				}
			}

			logger.debug("cookie中获取的账号：" + accountId);
			attributes.put(Constants.AccountId, accountId);
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}

}
