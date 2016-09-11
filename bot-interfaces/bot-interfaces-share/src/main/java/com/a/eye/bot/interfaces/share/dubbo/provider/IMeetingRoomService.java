package com.a.eye.bot.interfaces.share.dubbo.provider;

import com.a.eye.bot.interfaces.share.entity.MeetingRoom;

import java.util.List;

/**
 * @author: pengysh
 * @date 2016/9/9 0009 下午 3:00
 * @Description
 */
public interface IMeetingRoomService {
    public List<MeetingRoom> getMeetingRoomTime();
}
