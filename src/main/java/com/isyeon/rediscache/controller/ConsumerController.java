package com.isyeon.rediscache.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stream/consume")
@RequiredArgsConstructor
public class ConsumerController {
    private final RedisTemplate<String, Object> redis;
    private static final String GROUP = "myGroup";

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> consume(
            @RequestParam String streamKey,
            @RequestParam(defaultValue = "consumer1") String consumerId,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "2") long blockSeconds) {
        // 그룹 생성 (존재 시 예외 무시)
        try {
            redis.opsForStream().createGroup(streamKey, GROUP);
        } catch (Exception ignored) {
        }

        // 메시지 읽기
        List<MapRecord<String, Object, Object>> records = redis.opsForStream()
                .read(Consumer.from(GROUP, consumerId),
                        StreamReadOptions.empty()
                                .count(count)
                                .block(Duration.ofSeconds(blockSeconds)),
                        StreamOffset.create(streamKey, ReadOffset.lastConsumed()));

        if (records.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // 결과 매핑 및 ACK
        List<Map<String, Object>> result = new ArrayList<>();
        records.forEach(record -> {
            Map<String, Object> message = new HashMap<>();
            message.put("id", record.getId().getValue());

            // Map<Object, Object> -> Map<String, Object>로 변환
            Map<Object, Object> value = record.getValue();
            value.forEach((key, val) -> {
                // Object 타입을 String으로 변환하여 추가
                message.put(key.toString(), val);
            });

            result.add(message);
            redis.opsForStream().acknowledge(streamKey, GROUP, record.getId());
        });

        return ResponseEntity.ok(result);
    }
}

