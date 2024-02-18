package com.chat.chattingserver.common.aop.annotation;

public enum RoomType {
    INDIVIDUAL("individual"),
    GROUP("group");

    private final String key;

    RoomType(final String key) {
        this.key = key;
    }
}
