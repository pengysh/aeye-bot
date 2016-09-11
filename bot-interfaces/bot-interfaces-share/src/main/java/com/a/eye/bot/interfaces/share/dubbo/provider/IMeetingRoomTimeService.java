package com.a.eye.bot.interfaces.share.dubbo.provider;

import com.a.eye.bot.interfaces.share.entity.MeetingRoomTime;

import java.util.List;

/**
 * @author: pengysh
 * @date 2016/9/9 0009 下午 3:03
 * @Description
 */
public interface IMeetingRoomTimeService {

    public void insertMeetingRoomTime(MeetingRoomTime meetingRoomTime);

    public List<MeetingRoomTime> getMeetingRoomTime(String meetingRoomId);
}
