package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.ChatType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
        @NotNull
        @Schema(description = "Room Id", nullable = false, example = "test_room")
        private Long roomId;

        @NotNull
        @Schema(description = "Cursor", nullable = false, example = "999999")
        private Long cursor;

        @NotNull
        @Schema(description = "Message", nullable = false, example = "text_message")
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
        @NotNull
        @Schema(description = "User Id", nullable = false, example = "test_user")
        private String userId;

        @NotNull
        @Schema(description = "Room Id", nullable = false, example = "1")
        private Long roomId;

        @NotNull
        @Schema(description = "Type", nullable = false, example = "TEXT")
        private ChatType type;

        @NotNull
        @Schema(description = "Message", nullable = false, example = "text_message")
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