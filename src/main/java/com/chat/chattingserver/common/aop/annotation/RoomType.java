package com.chat.chattingserver.common.aop.annotation;


import lombok.Getter;


public enum RoomType {
    INDIVIDUAL("individual"),
    FRIEND("friend");

    private final String key;

    RoomType(final String key) {
        this.key = key;
    }
}
