package com.ai.bot.web.service.entity;

public class ChatMessageGroup {
    private Integer groupId;

    private String title;

    private String accountIds;

    private Integer idsCount;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(String accountIds) {
        this.accountIds = accountIds == null ? null : accountIds.trim();
    }

    public Integer getIdsCount() {
        return idsCount;
    }

    public void setIdsCount(Integer idsCount) {
        this.idsCount = idsCount;
    }
}