package com.a.eye.bot.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String DayDataFormatStr = "yyyy-MM-dd";

	public static final DateFormat dayDateFormat = new SimpleDateFormat(DayDataFormatStr);

	public static final String DataFormatStr = "yyyy-MM-dd HH:mm:ss";

	private static final DateFormat dateFormat = new SimpleDateFormat(DataFormatStr);

	public static final String ToDoDataFormatStr = "yyyy年MM月dd日 HH:mm";

	public static final DateFormat todoDateFormat = new SimpleDateFormat(ToDoDataFormatStr);

	public static Date parse(String dateStr) {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String dateStr, DateFormat dateFormat) {
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parse(long time) {
		return dateFormat.format(new Date(time));
	}

	/**
	 * 获取指定月份的第一天
	 * 
	 * @param month
	 * @return
	 */
	public static Date getTheMonthOfFirstDay(int month) {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		System.out.println(DateUtil.parse(calendar.getTime().getTime()));
		return calendar.getTime();
	}

	/**
	 * 获取指定月份的最后一天
	 * 
	 * @param month
	 * @return
	 */
	public static Date getTheMonthOfLastDay(int month) {
		Calendar calendar = Calendar.getInstance();// 获取当前日期
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		System.out.println(DateUtil.parse(calendar.getTime().getTime()));
		return calendar.getTime();
	}

	public static long getDistanceTimes(Date startTime, Date endTime) {
		long day = 0;
		long min = 0;
		long time1 = startTime.getTime();
		long time2 = endTime.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		min = ((diff / (60 * 1000)) - day * 24 * 60);
		return min;
	}
}
