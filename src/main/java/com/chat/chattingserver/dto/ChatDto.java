package com.chat.chattingserver.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

public class ChatDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ChatMessageRequest
    {
        private Long roomId;
        private Long cursor;
        private String message;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ChatMessageCreateRequest
    {
        private Long roomId;
        private String userId;
        private String message;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ChatMessageResponse
    {
        private Long id;
        private String userName;
        private String roomName;
        private String message;
        private Instant createdAt;
        private Instant updatedAt;
    }


}