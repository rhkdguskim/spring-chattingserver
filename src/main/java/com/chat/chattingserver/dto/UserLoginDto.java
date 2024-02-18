package com.chat.chattingserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserLoginDto {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request
    {
        private String user_id;
        private String password;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response
    {
        private String jwt_token;
    }
}
