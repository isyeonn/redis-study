package com.isyeon.rediscache.infrastructure.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());

        System.out.println("== Received message ==");
        System.out.println("Channel: " + channel);
        System.out.println("Message: " + body);

        // JSON 파싱 후 처리할 수도 있음
        // e.g. new ObjectMapper().readValue(body, ChatDto.class);
    }
}
