package com.a.eye.bot.time.message;

import com.a.eye.bot.common.message.BotAckMessage;

import java.util.Date;

public class AskMeWithinTimeAckMessage extends BotAckMessage {

    private String id;
    private Date startTime;
    private Date endTime;
    private String state;

    public AskMeWithinTimeAckMessage(String id, Date startTime, Date endTime, String state) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getState() {
        return state;
    }
}
