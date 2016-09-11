package com.a.eye.bot.interfaces.service.service;

import com.a.eye.bot.interfaces.service.dao.MeetingRoomMapper;
import com.a.eye.bot.interfaces.share.dubbo.provider.IMeetingRoomService;
import com.a.eye.bot.interfaces.share.entity.MeetingRoom;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MeetingRoomService implements IMeetingRoomService {

	@Autowired
	private MeetingRoomMapper meetingRoomMapper;

	public List<MeetingRoom> getMeetingRoomTime() {
		List<MeetingRoom> roomList = meetingRoomMapper.selectMeetingRoom("beijing");
		return roomList;
	}

}
