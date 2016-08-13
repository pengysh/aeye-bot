package com.ai.bot.web.service.entity;

public class ChatFriends {
    private Long accountId;

    private String friends;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends == null ? null : friends.trim();
    }
}