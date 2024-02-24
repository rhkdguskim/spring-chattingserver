package com.chat.chattingserver.dto;


import com.chat.chattingserver.common.aop.annotation.ChatMessageType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatMessageDto<T> {
    private ChatMessageType type;
    private T payload;
}
