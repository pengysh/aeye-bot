package com.ai.system.interfaces.dao;

import com.ai.system.interfaces.entity.MeetingRoomTime;
import com.ai.system.interfaces.entity.MeetingRoomTimeExample;
import java.util.List;

public interface MeetingRoomTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoomTime record);

    int insertSelective(MeetingRoomTime record);

    List<MeetingRoomTime> selectByExample(MeetingRoomTimeExample example);

    MeetingRoomTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoomTime record);

    int updateByPrimaryKey(MeetingRoomTime record);
}