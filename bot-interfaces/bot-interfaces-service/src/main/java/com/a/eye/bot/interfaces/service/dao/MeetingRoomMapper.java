package com.a.eye.bot.interfaces.service.dao;

import com.a.eye.bot.interfaces.share.entity.MeetingRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MeetingRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoom record);

    int insertSelective(MeetingRoom record);

    MeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoom record);

    int updateByPrimaryKey(MeetingRoom record);

    @Select("select * from meeting_room where region = #{region}")
    @ResultMap("BaseResultMap")
    List<MeetingRoom> selectMeetingRoom(@Param("region") String region);
}