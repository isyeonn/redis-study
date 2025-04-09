package com.isyeon.rediscache.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChatMessageDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerializationAndDeserialization() throws JsonProcessingException {
        // 1. 객체 생성
        ChatMessageDto original = ChatMessageDto.builder()
                .type(ChatMessageDto.MessageType.TALK)
                .roomId("room123")
                .sender("alice")
                .message("안녕하세요!")
                .timestamp("2025-04-09T15:00:00")
                .build();

        // 2. 직렬화: 객체 -> JSON 문자열
        String json = objectMapper.writeValueAsString(original);
        System.out.println("Serialized JSON:\n" + json);

        // 3. 역직렬화: JSON 문자열 -> 객체
        ChatMessageDto deserialized = objectMapper.readValue(json, ChatMessageDto.class);

        // 4. 검증
        assertThat(deserialized).isEqualTo(original);
    }
}