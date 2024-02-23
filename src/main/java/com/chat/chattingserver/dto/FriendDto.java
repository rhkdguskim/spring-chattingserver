package com.chat.chattingserver.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

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
        private String userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private List<UserDto.Response> friends;
    }

    public static class Add {

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Request {

            @Schema(description = "User ID", nullable = true, example = "test_friend2")
            private String userId;

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
        public static class Response {
            private Long FriendId;
            private String friendName;
        }
    }

    public static class Delete {

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Request
        {
            @Schema(description = "User ID", nullable = false, example = "userId")
            private String userId;

            @Schema(description = "Friend ID", nullable = false, example = "1")
            private Long friendId;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Response
        {
            private String message;
        }
    }

    public static class Modify {
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Request {
            private long friendId;
            private String userId;
            private String friendName;
        }
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Response {
            private String message;
        }


    }
}
