package com.a.eye.bot.chat.service.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.a.eye.bot.chat.service.dao.IUserStateDao;
import com.a.eye.bot.chat.service.entity.UserState;

@Repository
public class MongoUserStateDao implements IUserStateDao {

	@Autowired
	private MongoTemplate template;

	@Override
	public void saveUserState(Long userId, String state) {
		template.insert(new UserState(userId, state));
	}

	@Override
	public void updateUserState(Long userId, String state) {
		Query query = new Query(Criteria.where("userId").is(userId));
		template.updateFirst(query, Update.update("state", state), UserState.class);
	}

	public String selectUserState(Long userId) {
		Query query = new Query(Criteria.where("userId").is(userId));
		UserState userState = template.findOne(query, UserState.class);
		return userState.getState();
	}
}
