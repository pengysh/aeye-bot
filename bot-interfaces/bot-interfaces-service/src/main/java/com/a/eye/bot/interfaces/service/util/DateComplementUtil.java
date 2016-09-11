package com.a.eye.bot.interfaces.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DateComplementUtil {

	public long[][] multipleMinus(long[] range, long[][] sections) {
		HashSet<Long> set = new HashSet<Long>();
		for (long[] bs : sections) {
			long[] result = this.rangeMinus(range, bs);
			for (long r : result) {
				set.add(r);
			}
		}

		final List<Long> list = new ArrayList<Long>();
		for (final Long value : set) {
			list.add(value);
		}
		Collections.sort(list);

		long[][] resultSection = new long[list.size() / 2][2];
		for (int i = 0; i < list.size(); i = i + 2) {
			long[] section = { list.get(i), list.get(i + 1) };
			resultSection[i / 2] = section;
		}

		return resultSection;
	}

	/**
	 * 整数区域减法计算：as - bs
	 * 
	 * @param as
	 *            区域，数组长度为2的整型，0为起始值，1为结束值
	 * @param bs
	 *            区域，数组长度为2的整型，0为起始值，1为结束值
	 * @return 结果区域，偶数下标为起始值，奇数下标为结束值，数组长度可能为0、2、4
	 */
	private long[] rangeMinus(long[] as, long[] bs) {
		System.out.println(as[0] + "," + as[1] + " - " + bs[0] + "," + bs[1]);
		if (bs[0] > as[1] || bs[1] < as[0]) {
			return as;
		} else if (bs[0] == as[0] && bs[1] == as[1]) {
			return new long[0];
		} else if (bs[0] <= as[0]) {
			return new long[] { bs[1] + 1, as[1] };
		} else if (bs[1] >= as[1]) {
			return new long[] { as[0], bs[0] - 1 };
		} else {
			return new long[] { as[0], bs[0] - 1, bs[1] + 1, as[1] };
		}
	}
}
