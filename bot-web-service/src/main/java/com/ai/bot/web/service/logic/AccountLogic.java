package com.ai.bot.web.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.bot.web.service.dao.AccountMapper;
import com.ai.bot.web.service.entity.Account;
import com.ai.bot.web.service.entity.AccountExample;
import com.ai.bot.web.service.entity.AccountExample.Criteria;
import com.ai.bot.web.service.util.Constants;
import com.ai.bot.web.service.view.PersonContactView;
import com.google.gson.Gson;

@Service
@Transactional
public class AccountLogic {

	@Autowired
	private AccountMapper accountMapper;

	public List<Account> getContacts(Long companyId) {
		AccountExample accountExample = new AccountExample();
		Criteria criteria = accountExample.createCriteria();
		criteria.andCompanyIdEqualTo(companyId);
		return accountMapper.selectByExample(accountExample);
	}

	public void updateState(Long id, String state) {
		Account existAccount = accountMapper.selectByPrimaryKey(id);
		existAccount.setState(state);
		accountMapper.updateByPrimaryKey(existAccount);
	}

	public String registerAccount(Account account) {
		AccountExample accountExample = new AccountExample();
		Criteria criteria = accountExample.createCriteria();
		criteria.andAccountIdEqualTo(account.getAccountId());
		List<Account> accountList = accountMapper.selectByExample(accountExample);
		if (accountList != null && accountList.size() > 0) {
			return "账号已被注册";
		}

		int num = (int) (1 + Math.random() * (15 - 1 + 1));
		account.setHeadimage("static/image/" + num + ".png");
		account.setState(Constants.Offline);

		accountMapper.insert(account);
		return "success";
	}

	public Account query(String accountId, String password) {
		AccountExample accountExample = new AccountExample();
		Criteria criteria = accountExample.createCriteria();
		criteria.andAccountIdEqualTo(accountId);
		criteria.andPasswordEqualTo(password);
		List<Account> accountList = accountMapper.selectByExample(accountExample);
		return accountMapper.selectByPrimaryKey(accountId);
	}

	public String getPersonContacts(String accountId) {
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andAccountIdNotEqualTo(accountId);
		List<Account> accountList = accountMapper.selectByExample(accountExample);

		Gson gson = new Gson();
		return gson.toJson(new PersonContactView(Constants.PersonContacts, accountList));
	}
}
