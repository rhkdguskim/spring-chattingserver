package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UserDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserInfoRequest {
        @NotNull
        @Schema(description = "User Id", nullable = false, example = "test_user")
        private String userId;

        @NotNull
        @Schema(description = "password", nullable = false, example = "test_password")
        private String password;

        @Schema(description = "Name", nullable = false, example = "KimKwnagHyeon")
        private String name;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserInfo {
        private long id;
        private String userId;
        private String name;
        private String statusMsg;
        private UserRole role;
    }


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class LoginRequest {

        @NotNull
        @Schema(description = "userId", nullable = false, example = "test_user")
        private String userId;

        @NotNull
        @Schema(description = "password", nullable = false, example = "password")
        private String password;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class LoginResponse
    {
        private String jwtToken;
    }


}
