package com.isyeon.rediscache.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 캐시에 데이터 저장
    public void setCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 캐시에서 데이터 조회
    public String getCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 캐시에서 데이터 삭제
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }
}