package com.a.eye.bot.common.cache.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: UserChatJedisRepository.java
 * @author: pengysh
 * @date 2016/8/21 0021 下午 4:29
 * @Description:用户对话信息缓存
 */
@Repository
public class UserChatJedisRepository {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    /**
     * @author: pengysh
     * @date 2016/8/21 0021 下午 5:01
     * @Description：
     */
    public List<String> getLastOntology(final long userId) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.lastOntology.id." + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String content = redisTemplate.getStringSerializer().deserialize(value);
                    List<String> list = gson.fromJson(content, new TypeToken<List<String>>() {
                    }.getType());
                    return list;
                }
                return null;
            }
        });
    }

    /**
     * @author: pengysh
     * @date 2016/8/21 0021 下午 5:16
     * @Description：
     */
    public void saveLastOntology(final long userId, final List<String> ontology) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.lastOntology.id." + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String content = gson.toJson(ontology);
                    connection.set(redisTemplate.getStringSerializer().serialize("user.lastOntology.id." + userId), redisTemplate.getStringSerializer().serialize(content));
                }
                return null;
            }
        });
    }

    public void saveLastAnswer(final long userId, final String lastAnswer) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize("user.lastAnswer.id." + userId), redisTemplate.getStringSerializer().serialize(lastAnswer));
                return null;
            }
        });
    }

    public String getLastAnswer(final long userId) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.lastAnswer.id." + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String content = redisTemplate.getStringSerializer().deserialize(value);
                    return content;
                }
                return null;
            }
        });
    }

    public void saveCount(final long userId, final Integer count) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize("user.count.id." + userId), redisTemplate.getStringSerializer().serialize(String.valueOf(count)));
                return null;
            }
        });
    }

    public Integer getCount(final long userId) {
        return redisTemplate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("user.count.id." + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String content = redisTemplate.getStringSerializer().deserialize(value);
                    return Integer.valueOf(content);
                }
                return null;
            }
        });
    }
}
