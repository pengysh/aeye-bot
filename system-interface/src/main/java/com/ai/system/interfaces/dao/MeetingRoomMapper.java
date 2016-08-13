package com.ai.system.interfaces.dao;

import com.ai.system.interfaces.entity.MeetingRoom;
import com.ai.system.interfaces.entity.MeetingRoomExample;
import java.util.List;

public interface MeetingRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoom record);

    int insertSelective(MeetingRoom record);

    List<MeetingRoom> selectByExample(MeetingRoomExample example);

    MeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoom record);

    int updateByPrimaryKey(MeetingRoom record);
}