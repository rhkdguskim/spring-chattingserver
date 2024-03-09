package com.chat.chattingserver.domain;

import lombok.*;

import java.time.Instant;


public class BroadCastMessage {


    @Getter
    @Setter
    public static class ChatMessage extends AbstractBroadCastMessage {
        private Long id;
        private String userName;
        private String roomName;
        private String message;
        private Instant createdAt;
        private Instant updatedAt;

        @Builder()
        public ChatMessage(Long id, String userName, String roomName, String message, Instant createdAt, Instant updatedAt) {
            super(ChatMessageType.ONMESSAGE);
            this.id = id;
            this.userName = userName;
            this.roomName = roomName;
            this.message = message;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }


    @Getter
    @Setter
    public static class LeaveMessage extends AbstractBroadCastMessage
    {
        private Long id;
        private String userName;
        private Instant createdAt;

        @Builder
        public LeaveMessage(Long id, String userName, Instant createdAt) {
            super(ChatMessageType.ONLEAVE);
            this.id = id;
            this.userName = userName;
            this.createdAt = createdAt;
        }
    }

    @Getter
    @Setter
    public static class EnterMessage extends AbstractBroadCastMessage
    {
        private Long id;
        private String userName;
        private Instant createdAt;

        @Builder
        public EnterMessage(Long id, String userName, Instant createdAt) {
            super(ChatMessageType.ONENTER);
            this.id = id;
            this.userName = userName;
            this.createdAt = createdAt;
        }
    }
}
