package com.chat.chattingserver.common.aop.annotation;

public enum ChatType {
    TEXT("text"),
    IMAGE("image"),
    VIDEO("video");
    private final String key;

    ChatType(final String key) {
        this.key = key;
    }
}
