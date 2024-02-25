package com.chat.chattingserver.domain;


import lombok.Getter;


public enum RoomType {
    INDIVIDUAL("individual"),
    FRIEND("friend");

    private final String key;

    RoomType(final String key) {
        this.key = key;
    }
}
