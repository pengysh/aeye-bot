package com.a.eye.bot.interfaces.service.dao;

import com.a.eye.bot.interfaces.service.entity.MeetingRoomTimeExample;
import com.a.eye.bot.interfaces.share.entity.MeetingRoomTime;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingRoomTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoomTime record);

    int insertSelective(MeetingRoomTime record);

    MeetingRoomTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoomTime record);

    int updateByPrimaryKey(MeetingRoomTime record);

    @Select("select * from meetingroomtime where meeting_room_id = #{meetingRoomId}")
    @ResultMap("BaseResultMap")
    List<MeetingRoomTime> selectMeetingRoomTime(@Param("meetingRoomId") Integer meetingRoomId);

    List<MeetingRoomTime> selectByExample(MeetingRoomTimeExample example);
}