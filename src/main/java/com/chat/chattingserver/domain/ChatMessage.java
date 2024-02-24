package com.chat.chattingserver.domain;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long id;
    private String userName;
    private String roomName;
    private String message;
    private Instant createdAt;
    private Instant updatedAt;
}
