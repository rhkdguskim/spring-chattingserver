package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.RoomType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

public class RoomDto {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomRequest
    {
        @NotNull
        @Schema(description = "User Id", nullable = false, example = "test_user")
        private String userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ParticipantInfo {
        private String userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomInfo
    {
        private Long roomId;
        private String roomName;
        private String lastChat;
        private List<UserDto.UserInfo> participants;
        private RoomType roomType;
        private Instant createdAt;
        private Instant updatedAt;
    }



    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AddRequest
    {
        @NotNull
        @Schema(description = "RoomName", nullable = false, example = "test_Room")
        private String roomName;

        @NotNull
        @Schema(description = "participants", nullable = false)
        private List<ParticipantInfo> participants;
    }
}
