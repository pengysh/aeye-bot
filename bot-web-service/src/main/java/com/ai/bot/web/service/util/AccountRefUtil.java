package com.ai.bot.web.service.util;

import java.util.Arrays;

public class AccountRefUtil {

	public static String getAccountRef(String accountId1, String accountId2) {
		String[] accountIds = { accountId1, accountId2 };
		Arrays.sort(accountIds);
		return accountIds[0] + "-" + accountIds[1];
	}
}
