package com.chat.chattingserver.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class FriendDto {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Schema(description = "User Id", nullable = false, example = "test_user")
        private String userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private List<UserDto.UserInfo> friends;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AddRequest {

        @Schema(description = "Friend ID", nullable = false, example = "1")
        private Long friendId;

        @Schema(description = "Friend Name", nullable = false, example = "test_name")
        private String friendName;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AddResponse {

        private Long FriendId;
        private String friendName;
    }
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteRequest {
        @Schema(description = "Friend ID", nullable = false, example = "1")
        private Long friendId;

    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteResponse {
        private String message;
    }


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ModifyRequest {

        @Schema(description = "Friend Id", nullable = false, example = "1")
        private long friendId;

        @Schema(description = "Friend Name", nullable = false, example = "test_friend")
        private String friendName;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ModifyResponse {
        private String message;
    }
}
