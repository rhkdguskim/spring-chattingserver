package com.chat.chattingserver.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class SessionData {
    private User user;
    private List<Room> rooms;
}
