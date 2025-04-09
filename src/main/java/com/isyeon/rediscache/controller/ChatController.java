package com.isyeon.rediscache.controller;

import com.isyeon.rediscache.service.RedisPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final RedisPublisher publisher;

    public ChatController(RedisPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/publish")
    public void publish(@RequestParam String message) {
        publisher.publish(message);
    }
}

