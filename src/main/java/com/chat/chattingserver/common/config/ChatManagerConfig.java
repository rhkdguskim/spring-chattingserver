package com.chat.chattingserver.common.config;

import com.chat.chattingserver.service.ChatService;
import com.chat.chattingserver.service.RoomService;
import com.chat.chattingserver.service.chatting.ChatMessageProcessor;
import com.chat.chattingserver.service.chatting.ChattingRoomService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ChatManagerConfig {
    private final RoomService roomService;
    private final ChatService chatService;

    @Bean
    public ChatMessageProcessor chatMessageProcessor() {
        return new ChattingRoomService(roomService, chatService);
    }
}
