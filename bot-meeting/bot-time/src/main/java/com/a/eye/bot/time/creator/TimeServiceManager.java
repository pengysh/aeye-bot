package com.a.eye.bot.time.creator;

import com.a.eye.bot.interfaces.share.dubbo.provider.IMeetingRoomTimeService;
import com.a.eye.bot.interfaces.share.entity.MeetingRoomTime;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: pengysh
 * @date 2016/9/9 0009 下午 3:09
 * @Description
 */
@Component
public class TimeServiceManager {

    @Reference
    private IMeetingRoomTimeService meetingRoomTimeService;

    public void insertMeetingRoomTime(MeetingRoomTime meetingRoomTime) {
        meetingRoomTimeService.insertMeetingRoomTime(meetingRoomTime);
    }

    public List<MeetingRoomTime> getMeetingRoomTime(String meetingRoomId) {
        return meetingRoomTimeService.getMeetingRoomTime(meetingRoomId);
    }
}
