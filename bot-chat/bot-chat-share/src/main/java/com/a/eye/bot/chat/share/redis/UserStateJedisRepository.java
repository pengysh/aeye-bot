package com.a.eye.bot.chat.share.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Title: UserStateJedisRepository.java
 * @author: pengysh
 * @date 2016年8月13日 下午1:24:05
 * @Description:用户状态存储
 */
@Repository
public class UserStateJedisRepository {

	@Autowired
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	/**
	 * @Title: modifyUserState
	 * @author: pengysh
	 * @date 2016年8月13日 下午1:24:17
	 * @Description:更新用户状态
	 * @param userId
	 * @param state
	 */
	public void modifyUserState(final Long userId, final String state) {
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(redisTemplate.getStringSerializer().serialize("user.state.id." + userId), redisTemplate.getStringSerializer().serialize(state));
				return null;
			}
		});
	}

	/**
	 * @Title: selectUserState
	 * @author: pengysh
	 * @date 2016年8月13日 下午1:24:52
	 * @Description:获取用户状态
	 * @param userId
	 * @return
	 */
	public String selectUserState(final long userId) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("user.state.ui." + userId);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					return redisTemplate.getStringSerializer().deserialize(value);
				}
				return null;
			}
		});
	}
}
