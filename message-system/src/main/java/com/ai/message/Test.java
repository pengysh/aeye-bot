package com.ai.message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar ca = Calendar.getInstance();
		ca.set(2016, 7, 30, 3, 30, 30);
		long start1 = ca.getTimeInMillis();
		ca.set(2016, 7, 30, 4, 30, 30);
		long end1 = ca.getTimeInMillis();

		ca.set(2016, 7, 30, 0, 0, 0);
		long s = ca.getTimeInMillis();
		ca.set(2016, 7, 30, 24, 0, 0);
		long e = ca.getTimeInMillis();

		long[] as = { 0, 50 };
		// long[] bs = { 3, 4 };
		long[][] cs = { { 4, 10 }, { 15, 20 }, { 30, 40 } };

		HashSet<Long> set = new HashSet<Long>();
		for (long[] bs : cs) {
			long[] result = Test.rangeMinus(as, bs);
			for (long r : result) {
				set.add(r);
			}
		}

		final List<Long> list = new ArrayList<Long>();
		for (final Long value : set) {
			list.add(value);
		}
		Collections.sort(list);
		
		for(Long value : list){
			System.out.println(value);
		}
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
	public static long[] rangeMinus(long[] as, long[] bs) {
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
