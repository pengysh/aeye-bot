package com.a.eye.bot.common.message;

public class CmdingAckMessage extends BotAckMessage {
	private String replyMessage = "上一条命令未处理完成，请等待系统恢复后再发送新的命令";

	public String getReplyMessage() {
		return replyMessage;
	}
}
