package com.a.eye.bot.chat.ui.websocket;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.a.eye.bot.chat.ui.service.UserStateRedisService;
import com.a.eye.bot.common.cmd.Cmd;
import com.a.eye.bot.common.cmd.CmdExecuter;
import com.a.eye.bot.common.ui.consts.Constants;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;

/**
 * @Title: ChatWebSocketHandler.java
 * @author: pengysh
 * @date 2016年8月13日 下午1:49:04
 * @Description:对话模块服务器端入口
 */
public class ChatWebSocketHandler implements WebSocketHandler {

	private static Logger logger = LogManager.getLogger(ChatWebSocketHandler.class.getName());

	private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

	private Gson gson = new Gson();

	@Autowired
	private UserStateRedisService userStateRedisService;

	/**
	 * @Title: afterConnectionEstablished
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:00:10
	 * @Description:连接
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("ConnectionEstablished");
		users.add(session);

		// 更新用户状态为在线
		Long userId = (Long) session.getAttributes().get(Constants.UserId);
		userStateRedisService.userOnline(userId);
	}

	/**
	 * @Title: handleMessage
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:00:25
	 * @Description:接收消息
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String messageStr = message.getPayload().toString();
		logger.debug("message PayloadLength：" + message.getPayloadLength() + ",payload: " + messageStr);

		if (message.getPayloadLength() > 5) {
			Cmd cmd = gson.fromJson(messageStr, Cmd.class);
			CmdExecuter executer = SpringContextUtil.getBean(cmd.getCmd());
			executer.exe(cmd.getUserId(), cmd.getContent());
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
		logger.debug("handleTransportError" + exception.getMessage());
	}

	/**
	 * @Title: afterConnectionClosed
	 * @author: pengysh
	 * @date 2016年8月13日 下午3:00:42
	 * @Description:连接关闭
	 * @param session
	 * @param closeStatus
	 * @throws Exception
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		users.remove(session);
		logger.debug("afterConnectionClosed" + closeStatus.getReason());

		// 更新用户状态为在线
		Long userId = (Long) session.getAttributes().get(Constants.UserId);
		userStateRedisService.userOffline(userId);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
