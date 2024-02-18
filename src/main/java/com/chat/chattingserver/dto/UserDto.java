package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import lombok.*;

public class UserDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String user_id;
        private String password;
        private String name;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String user_id;
        private String name;
        private String status_msg;
        private UserRole role;
    }

}
