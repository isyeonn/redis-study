package com.isyeon.rediscache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    public enum MessageType {
        ENTER,    // 입장
        TALK,     // 일반 메시지
        LEAVE     // 퇴장
    }

    private MessageType type;  // 메시지 타입 (ENTER, TALK, LEAVE)
    private String roomId;     // 방 ID
    private String sender;     // 보낸 사람
    private String message;    // 실제 메시지 내용
    private String timestamp;  // 전송 시간 (ISO 또는 원하는 포맷)
}
