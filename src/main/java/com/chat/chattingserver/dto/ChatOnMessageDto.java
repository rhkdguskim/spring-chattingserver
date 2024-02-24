package com.chat.chattingserver.dto;


import com.chat.chattingserver.common.aop.annotation.ChatType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChatOnMessageDto {
    private ChatType type;
    private String message;
    private Long roomId;
}

//{
//        "type": "ONMESSAGE",
//        "payload": {
//        "type" : "TEXT",
//        "message": "hi"
//        }
//        }