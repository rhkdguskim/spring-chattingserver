package com.chat.chattingserver.domain;


public abstract class AbstractBroadCastMessage {
    private final ChatMessageType messageType;

    AbstractBroadCastMessage(ChatMessageType messageType)
    {
        this.messageType = messageType;
    }
}
