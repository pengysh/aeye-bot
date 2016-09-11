package com.a.eye.bot.common.base;

public abstract class PersonalBotBase extends BotBase {

    private Long userId;

    private String userName;

    public PersonalBotBase(String id, Long userId, String userName) {
        super(id, null, null, StateBase.Idle);
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
