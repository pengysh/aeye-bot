package com.a.eye.bot.meetingscene.creator;


import com.a.eye.bot.common.base.CreatorBase;
import com.a.eye.bot.meetingscene.MeetingSceneBot;

public class MeetingSceneCreater extends CreatorBase<MeetingSceneBot> {

    private static final long serialVersionUID = 492371702000964028L;

    private Long userId;
    private String userName;

    public MeetingSceneCreater(String id, Long userId, String userName) {
        super(id);
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public MeetingSceneBot create() {
        return new MeetingSceneBot(getId(), userId, userName);
    }
}
