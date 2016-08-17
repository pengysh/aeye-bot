package com.a.eye.bot.common.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.a.eye.bot.common.cache.user.entity.UserCacheInfo;
import com.google.gson.Gson;

/**
 * @Title: UserInfoJedisRepository.java
 * @author: pengysh
 * @date 2016年8月13日 下午5:27:59
 * @Description:用户信息缓存
 */
@Repository
public class UserInfoJedisRepository {

	@Autowired
	private StringRedisTemplate redisTemplate;

	private Gson gson = new Gson();

	/**
	 * @Title: saveUserInfo
	 * @author: pengysh
	 * @date 2016年8月13日 下午5:31:57
	 * @Description:用户信息缓存
	 * @param userInfo
	 */
	public void saveUserInfo(final UserCacheInfo userInfo) {
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				String userInfoJson = gson.toJson(userInfo);
				connection.set(redisTemplate.getStringSerializer().serialize("user.info.id." + userInfo.getUserId()), redisTemplate.getStringSerializer().serialize(userInfoJson));
				return null;
			}
		});
	}

	/**
	 * @Title: selectUserInfo
	 * @author: pengysh
	 * @date 2016年8月13日 下午5:33:58
	 * @Description:获取用户信息
	 * @param userId
	 * @return
	 */
	public UserCacheInfo selectUserInfo(final long userId) {
		return redisTemplate.execute(new RedisCallback<UserCacheInfo>() {
			@Override
			public UserCacheInfo doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("user.info.id." + userId);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					String userInfoJson = redisTemplate.getStringSerializer().deserialize(value);
					return gson.fromJson(userInfoJson, UserCacheInfo.class);
				}
				return null;
			}
		});
	}
}
