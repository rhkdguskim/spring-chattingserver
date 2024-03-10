package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.service.chatting.ChattingRoomService;
import com.chat.chattingserver.service.chatting.UserSession;
import kotlin.random.Random;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChattingRoomServiceTest {

    @Autowired
    private ChatService chatService;

    @Autowired
    private RoomService roomService;

    private final ChattingRoomService chattingRoomService = new ChattingRoomService(roomService, chatService);
    @Slf4j
    private static class MockUserSession implements UserSession {
        @Override
        public void sendMessage(String message) {
          log.info(message);
        }

        @Override
        public String getId() {
            return Random.Default.toString();
        }
    }

    @Test
    @DisplayName("onConnected")
    public void onConnected()
    {
        chattingRoomService.onConnect(new MockUserSession(), new User());
    }

}
