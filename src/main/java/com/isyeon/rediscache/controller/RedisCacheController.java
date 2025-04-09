package com.isyeon.rediscache.controller;

import com.isyeon.rediscache.service.RedisCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisCacheController {
    private final RedisCacheService redisCacheService;

    public RedisCacheController(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    // 데이터 캐시 저장
    @GetMapping("/cache/set/{key}/{value}")
    public String setCache(@PathVariable String key, @PathVariable String value) {
        redisCacheService.setCache(key, value);
        return "Cache Set: " + key + " = " + value;
    }

    // 데이터 캐시 조회
    @GetMapping("/cache/get/{key}")
    public String getCache(@PathVariable String key) {
        String value = redisCacheService.getCache(key);
        return value != null ? "Cache Hit: " + key + " = " + value : "Cache Miss";
    }

    // 데이터 캐시 삭제
    @GetMapping("/cache/delete/{key}")
    public String deleteCache(@PathVariable String key) {
        redisCacheService.deleteCache(key);
        return "Cache Deleted: " + key;
    }
}
