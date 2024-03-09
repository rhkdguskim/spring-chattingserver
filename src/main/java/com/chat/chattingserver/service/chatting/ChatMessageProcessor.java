package com.chat.chattingserver.service.chatting;


import com.chat.chattingserver.domain.User;

public interface ChatMessageProcessor {
    void onConnect(UserSession session, User user);
    void onDisconnect(UserSession session);
    void onMessage(UserSession session, String message, Long roomId);
}