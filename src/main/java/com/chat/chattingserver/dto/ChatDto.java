package com.chat.chattingserver.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.Instant;

public class ChatDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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