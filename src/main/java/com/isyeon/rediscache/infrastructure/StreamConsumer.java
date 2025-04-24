package com.isyeon.rediscache.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class StreamConsumer implements InitializingBean {
    private final RedisTemplate<String, Object> redis;
    private static final String GROUP = "myGroup";
    private static final String CONSUMER = "consumer1";

    @Override
    public void afterPropertiesSet() throws Exception {
        // 그룹 생성 (최초 1회)
        try {
            redis.opsForStream().createGroup("mystream", GROUP);
        } catch (Exception e) { /* 이미 생성됐을 때 무시 */ }

        // 메시지 읽기 루프
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                List<MapRecord<String, Object, Object>> msgs = redis.opsForStream()
                        .read(Consumer.from(GROUP, CONSUMER),
                                StreamReadOptions.empty().count(10).block(Duration.ofSeconds(2)),
                                StreamOffset.create("mystream", ReadOffset.lastConsumed()));
                if (msgs != null) {
                    for (MapRecord<String, Object, Object> msg : msgs) {
                        System.out.println("Consumed: " + msg.getId() + " => " + msg.getValue());
                        // 확인
                        redis.opsForStream().acknowledge("mystream", GROUP, msg.getId());
                    }
                }
            }
        });
    }
}
