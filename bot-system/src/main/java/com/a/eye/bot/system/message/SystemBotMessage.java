package com.a.eye.bot.system.message;

import com.a.eye.bot.common.message.BotReqMessage;

public class SystemBotMessage extends BotReqMessage {

    private Long userId;

    private String userName;

    private String groupId;

    private String message;

    public SystemBotMessage(Long userId, String userName, String groupId, String message) {
        this.userId = userId;
        this.userName = userName;
        this.groupId = groupId;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public String getGroupId() {
        return groupId;
    }
}
