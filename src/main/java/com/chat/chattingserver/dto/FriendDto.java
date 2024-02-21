package com.chat.chattingserver.dto;


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
        private Long userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private List<UserDto.Response> users;
    }

    public static class Add {

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Request {
            private long userId;
            private long friendId;
            private String friendName;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Response {
            private long id;
            private String friendName;
        }
    }

    public static class Delete {

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Request
        {
            private long userId;
            private long friendId;
        }

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
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
        public static class Request {
            private long friendId;
            private long userId;
            private String friendName;
        }
        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Response {
            private String message;
        }


    }
}
