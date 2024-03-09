package com.chat.chattingserver.domain;

public enum ChatMessageType {
    ONMESSAGE("onmessage"),
    ONLEAVE("onleave"),
    ONENTER("onenter");
    ChatMessageType(final String key) {

    }
}
