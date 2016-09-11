package com.a.eye.bot.interfaces.service.service;

import com.a.eye.bot.interfaces.service.dao.MeetingRoomTimeMapper;
import com.a.eye.bot.interfaces.service.entity.MeetingRoomTimeExample;
import com.a.eye.bot.interfaces.service.util.DateComplementUtil;
import com.a.eye.bot.interfaces.share.dubbo.provider.IMeetingRoomTimeService;
import com.a.eye.bot.interfaces.share.entity.MeetingRoomTime;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MeetingRoomTimeService implements IMeetingRoomTimeService {

    @Autowired
    private MeetingRoomTimeMapper meetingRoomTimeMapper;

    public void insertMeetingRoomTime(MeetingRoomTime meetingRoomTime) {
        meetingRoomTimeMapper.insert(meetingRoomTime);
    }

    public List<MeetingRoomTime> getMeetingRoomTime(String meetingRoomId) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(new Date());
        startCalendar.add(Calendar.DATE, -15);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.add(Calendar.DATE, 15);

        List<MeetingRoomTime> allTimeList = new ArrayList<MeetingRoomTime>();

        MeetingRoomTimeExample betweenCondtion = new MeetingRoomTimeExample();
        MeetingRoomTimeExample.Criteria betweenCriteria = betweenCondtion.createCriteria();
        betweenCriteria.andStartTimeGreaterThanOrEqualTo(startCalendar.getTime());
        betweenCriteria.andEndTimeLessThanOrEqualTo(endCalendar.getTime());
        betweenCriteria.andMeetingRoomIdEqualTo(Integer.parseInt(meetingRoomId));
        List<MeetingRoomTime> betweenTimeList = meetingRoomTimeMapper.selectByExample(betweenCondtion);

        MeetingRoomTimeExample startTimeCondtion = new MeetingRoomTimeExample();
        MeetingRoomTimeExample.Criteria startCriteria = startTimeCondtion.createCriteria();
        startCriteria.andStartTimeLessThanOrEqualTo(startCalendar.getTime());
        startCriteria.andEndTimeGreaterThanOrEqualTo(startCalendar.getTime());
        startCriteria.andMeetingRoomIdEqualTo(Integer.parseInt(meetingRoomId));
        List<MeetingRoomTime> startTimeList = meetingRoomTimeMapper.selectByExample(startTimeCondtion);

        MeetingRoomTimeExample endTimeCondtion = new MeetingRoomTimeExample();
        MeetingRoomTimeExample.Criteria endCriteria = endTimeCondtion.createCriteria();
        endCriteria.andStartTimeLessThanOrEqualTo(endCalendar.getTime());
        endCriteria.andEndTimeGreaterThanOrEqualTo(endCalendar.getTime());
        endCriteria.andMeetingRoomIdEqualTo(Integer.parseInt(meetingRoomId));
        List<MeetingRoomTime> endTimeList = meetingRoomTimeMapper.selectByExample(endTimeCondtion);

        allTimeList.addAll(startTimeList);
        allTimeList.addAll(endTimeList);
        allTimeList.addAll(betweenTimeList);

        List<MeetingRoomTime> minusTimeList = this.minusTime(allTimeList, startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis(), meetingRoomId);
        allTimeList.addAll(minusTimeList);

        return allTimeList;
    }

    private List<MeetingRoomTime> minusTime(List<MeetingRoomTime> timeList, long start, long end, String meetingRoomId) {
        DateComplementUtil util = new DateComplementUtil();
        long[] range = {start, end};
        long[][] sections = new long[timeList.size()][2];
        for (int i = 0; i < timeList.size(); i++) {
            MeetingRoomTime meetingRoomTime = timeList.get(i);
            sections[i][0] = meetingRoomTime.getStartTime().getTime();
            sections[i][1] = meetingRoomTime.getEndTime().getTime();
        }
        long[][] minus = util.multipleMinus(range, sections);
        if (minus.length == 0) {
            minus = new long[1][2];
            minus[0] = range;
        }

        List<MeetingRoomTime> minusList = new ArrayList<MeetingRoomTime>();
        for (long[] oneMinus : minus) {
            MeetingRoomTime meetingRoomTime = new MeetingRoomTime();
            meetingRoomTime.setMeetingRoomId(Integer.valueOf(meetingRoomId));
            meetingRoomTime.setStartTime(new Date(oneMinus[0]));
            meetingRoomTime.setEndTime(new Date(oneMinus[1]));
            meetingRoomTime.setState("Idle");
            minusList.add(meetingRoomTime);
        }

        return minusList;
    }
}
