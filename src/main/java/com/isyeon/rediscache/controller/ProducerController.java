package com.isyeon.rediscache.controller;

import com.isyeon.rediscache.infrastructure.StreamProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stream/produce")
@RequiredArgsConstructor
public class ProducerController {
    private final StreamProducer producer;

    @PostMapping
    public ResponseEntity<String> produce(
            @RequestParam String streamKey,
            @RequestBody Map<String, String> data
    ) {
        RecordId id = producer.produce(streamKey, data);
        return ResponseEntity.ok("Produced message with ID: " + id);
    }
}
