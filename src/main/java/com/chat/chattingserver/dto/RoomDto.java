package com.chat.chattingserver.dto;

import com.chat.chattingserver.common.aop.annotation.RoomType;
import lombok.*;

import java.util.List;

public class RoomDto {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RoomRequest
    {
        private String userId;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class RoomResponse
    {
        private Long roomId;
        private String roomName;
        private String lastChat;
        private UserDto.Response participants;
        private RoomType roomType;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AddRequest
    {
        private String roomName;
        private List<UserDto.Response> participants;
    }


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class AddResponse
    {
        private Long roomId;
        private String roomName;
        private UserDto.Response participants;
    }
}
