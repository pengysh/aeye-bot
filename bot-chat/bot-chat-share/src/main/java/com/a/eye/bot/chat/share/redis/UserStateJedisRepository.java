package com.a.eye.bot.chat.share.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
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
	protected StringRedisTemplate stringRedisTemplate;

	/**
	 * @Title: modifyUserState
	 * @author: pengysh
	 * @date 2016年8月13日 下午1:24:17
	 * @Description:更新用户状态
	 * @param userId
	 * @param state
	 */
	public void modifyUserState(final Long userId, final String state) {
		stringRedisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(stringRedisTemplate.getStringSerializer().serialize("user.state.id." + userId), stringRedisTemplate.getStringSerializer().serialize(state));
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
		return stringRedisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = stringRedisTemplate.getStringSerializer().serialize("user.state.ui." + userId);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					return stringRedisTemplate.getStringSerializer().deserialize(value);
				}
				return null;
			}
		});
	}
}
