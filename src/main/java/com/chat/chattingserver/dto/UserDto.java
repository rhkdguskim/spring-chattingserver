package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Request {
        @NotNull
        private String userId;

        @NotNull
        private String password;
        private String name;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        private long id;
        private String userId;
        private String name;
        private String statusMsg;
        private UserRole role;
    }

    public static class Login {
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @ToString
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Request
        {
            private String userId;
            private String password;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @ToString
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class Response
        {
            private String jwtToken;
        }
    }

}
