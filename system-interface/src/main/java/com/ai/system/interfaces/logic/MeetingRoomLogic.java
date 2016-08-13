package com.ai.system.interfaces.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.system.interfaces.dao.MeetingRoomMapper;
import com.ai.system.interfaces.entity.MeetingRoom;
import com.ai.system.interfaces.entity.MeetingRoomExample;
import com.ai.system.interfaces.entity.MeetingRoomExample.Criteria;

@Service
@Transactional
public class MeetingRoomLogic {

	@Autowired
	private MeetingRoomMapper meetingRoomMapper;

	public List<MeetingRoom> getMeetingRoomTime() {
		MeetingRoomExample example = new MeetingRoomExample();
		Criteria criteria = example.createCriteria();
		List<MeetingRoom> roomList = meetingRoomMapper.selectByExample(example);
		return roomList;
	}

}
