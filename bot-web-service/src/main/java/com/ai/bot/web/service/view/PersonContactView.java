package com.ai.bot.web.service.view;

import java.util.List;

import com.ai.bot.web.service.entity.Account;

public class PersonContactView extends ViewBase {

	public PersonContactView(String contentId, List<Account> accountList) {
		super(contentId);
		this.accountList = accountList;
	}

	private List<Account> accountList;

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}
}
