package com.chat.chattingserver.common.config;

import com.chat.chattingserver.service.chatting.ChatMessageProcessor;
import com.chat.chattingserver.service.ChatService;
import com.chat.chattingserver.service.chatting.ChattingRoomService;
import com.chat.chattingserver.service.RoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChattingRoomServiceConfig {

    private final ChattingRoomService chattingRoomServiceImpl;
    ChattingRoomServiceConfig(RoomService roomService, ChatService chatService)
    {
        chattingRoomServiceImpl = new ChattingRoomService(roomService, chatService);
    }

    @Bean
    public ChatMessageProcessor chatMessageProcessor(RoomService roomService, ChatService chatService) {
        return chattingRoomServiceImpl;
    }
}
