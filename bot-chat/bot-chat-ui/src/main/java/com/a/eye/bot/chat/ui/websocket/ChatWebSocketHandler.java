package com.a.eye.bot.chat.ui.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.a.eye.bot.chat.share.conts.UserStateConstants;
import com.a.eye.bot.chat.ui.service.UserStateRedisService;
import com.a.eye.bot.chat.ui.websocket.cmd.UserStateNoticeProducerExecuter;
import com.a.eye.bot.common.message.cmd.Cmd;
import com.a.eye.bot.common.message.cmd.CmdExecuter;
import com.a.eye.bot.common.ui.consts.Constants;
import com.a.eye.bot.common.util.SpringContextUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @Title: ChatWebSocketHandler.java
 * @author: pengysh
 * @date 2016年8月13日 下午1:49:04
 * @Description:对话模块服务器端入口
 */
public class ChatWebSocketHandler implements WebSocketHandler {

	private static Logger logger = LogManager.getLogger(ChatWebSocketHandler.class.getName());

	private static final Map<Long, WebSocketSession> users = new HashMap<Long, WebSocketSession>();

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
		// 更新用户状态为在线
		Long userId = (Long) session.getAttributes().get(Constants.UserId);
		this.userStateNotice(userId, UserStateConstants.Online_State);

		users.put(userId, session);
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
			try {
				Cmd cmd = gson.fromJson(messageStr, Cmd.class);
				logger.debug("客户端发送的命令：" + cmd.getProducerCmd());
				CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
				logger.debug("命令执行实例：" + executer.getClass());
				executer.sendMessage(cmd.getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		this.userStateNotice(userId, UserStateConstants.Offline_State);
		userStateRedisService.userOffline(userId);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * @Title: sendMessageToUser
	 * @author: pengysh
	 * @date 2016年8月15日 下午1:07:06
	 * @Description:向用户推送消息
	 * @param userId
	 * @param message
	 */
	public static void sendMessageToUser(Long userId, TextMessage message) {
		WebSocketSession session = users.get(userId);
		if (session != null) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: userStateNotice
	 * @author: pengysh
	 * @date 2016年8月16日 下午5:07:31
	 * @Description:用户上线和离线状态通知
	 * @param userId
	 * @param state
	 */
	private void userStateNotice(Long userId, String state) {
		JsonObject content = new JsonObject();
		content.addProperty(UserStateNoticeProducerExecuter.UserId_Param, userId);
		content.addProperty(UserStateNoticeProducerExecuter.State_Param, state);

		Cmd cmd = new Cmd();
		cmd.setCmd("UserStateNotice");
		cmd.setContent(content);
		CmdExecuter executer = SpringContextUtil.getBean(cmd.getProducerCmd());
		logger.debug("命令执行实例：" + executer.getClass());
		executer.sendMessage(cmd.getContent());
	}
}
