package com.chat.chattingserver.common.aop.annotation;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin-id"),
    NORMAL("normal-id");

    private final String key;

    UserRole(final String key) {
        this.key = key;
    }
}