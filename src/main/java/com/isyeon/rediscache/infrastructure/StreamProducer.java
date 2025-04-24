package com.isyeon.rediscache.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StreamProducer {
    private final RedisTemplate<String, Object> redis;

    public RecordId produce(String streamKey, Map<String, String> data) {
        RecordId id = redis.opsForStream().add(streamKey, data);
        System.out.println("Produced: " + id);
        return id;
    }
}