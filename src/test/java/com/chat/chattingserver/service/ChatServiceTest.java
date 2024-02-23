package com.chat.chattingserver.service;

import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.service.chat.ChatService;
import com.chat.chattingserver.service.chat.ChattingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatServiceTest {

    private final Logger logger = LoggerFactory.getLogger(ChattingService.class.getName());
    private final static String userId = "TestUser";
    private final static String roomName = "TestRoom";
    private final int chattingCnt = 500;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ChatService chatService;

    @BeforeAll()
    public void init()
    {
        UserDto.Request user = UserDto.Request.builder()
                .name("TestUser")
                .password("test1234")
                .userId(userId)
                .build();
        UserDto.Response createdUser = userService.Register(user);

        logger.info(createdUser.toString());

        ArrayList<UserDto.Response> participants = new ArrayList<>();
        participants.add(createdUser);

        RoomDto.AddRequest request = RoomDto.AddRequest.builder()
                .roomName(roomName)
                .participants(participants)
                .build();

        RoomDto.AddResponse room = roomService.CreateRoom(request);

        List<RoomDto.RoomResponse> createdRoom = roomService.findRoom(RoomDto.RoomRequest.builder()
                .userId(userId)
                .build());

        logger.info(createdRoom.toString());

        for (int i = 0; i < chattingCnt; i ++)
        {
            for (RoomDto.RoomResponse r : createdRoom) {
                logger.info(chatService.CreateChatMessage(ChatDto.ChatMessageCreateRequest.builder()
                        .roomId(r.getRoomId())
                        .message("TestMessage" + i)
                        .userId(createdUser.getUserId())
                        .build()).toString());
            }
        }
    }

    @Test
    @DisplayName("Pageing Chattings")
    public void PageChatting()
    {
        var user = userService.FindUserByID(userId);
        var rooms = roomService.findRoom(RoomDto.RoomRequest.builder()
                .userId(user.getUserId())
                .build());

        int cnt = 0;
        var cursor = 99999999L;
        while (true)
        {
            var chattings = chatService.GetChatMessageByCursor(ChatDto.ChatMessageRequest.builder()
                    .cursor(cursor)
                    .roomId(rooms.getFirst().getRoomId())
                    .build());

            if(chattings.isEmpty())
                break;

            cursor = chattings.getLast().getId();
            cnt += chattings.size();
        }
        assertThat(cnt).isEqualTo(chattingCnt);
    }
}
