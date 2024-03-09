package com.chat.chattingserver.service.chatting;

public interface UserSession {
    void sendMessage(String message);
    String getId();
}
