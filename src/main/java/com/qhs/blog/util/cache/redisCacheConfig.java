package com.qhs.blog.util.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * 总之先引入缓存，后面何时用到再说
 */

@Configuration
//@EnableCaching
public class redisCacheConfig  extends CachingConfigurerSupport {
    @Resource(name = "redisTemplate")
    protected ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    protected RedisTemplate<String, String> redisTemplate;


    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        return cacheManager;
    }



}
