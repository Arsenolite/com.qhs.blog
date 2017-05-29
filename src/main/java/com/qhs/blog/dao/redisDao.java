package com.qhs.blog.dao;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by QHS on 2017/5/28.
 */
@Repository
public class redisDao {

    @Resource(name = "redisTemplate")
    protected ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    protected RedisTemplate<String, String> redisTemplate;

    public void addValue(String key, String value){
        valueOperations.set(key, value);
    }

    public String getValue(String key){
        return valueOperations.get(key);
    }

    /**
     * 设置超时
     * @param key
     * @param timeout
     * @param unit
     * @author lianggz
     */
    public void expire(String key, final long timeout, final TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}
