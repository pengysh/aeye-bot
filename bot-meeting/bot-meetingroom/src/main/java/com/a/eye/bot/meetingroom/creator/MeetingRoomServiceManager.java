package com.a.eye.bot.meetingroom.creator;

import com.a.eye.bot.interfaces.share.dubbo.provider.IMeetingRoomService;
import com.a.eye.bot.interfaces.share.entity.MeetingRoom;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: pengysh
 * @date 2016/9/9 0009 下午 3:09
 * @Description
 */
@Component
public class MeetingRoomServiceManager {

    @Reference
    private IMeetingRoomService meetingRoomService;

    public List<MeetingRoom> getMeetingRoomTime() {
        return meetingRoomService.getMeetingRoomTime();
    }
}
