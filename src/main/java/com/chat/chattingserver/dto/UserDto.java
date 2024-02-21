package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
        public static class Request
        {
            private String userId;
            private String password;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Response
        {
            private String jwtToken;
        }
    }

}
