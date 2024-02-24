package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatServiceTest {
    private final static String userId = "TestUser";
    private final static String roomName = "TestRoom";
    private final static String message = "TestMessage";


    @Autowired
    private ModelMapper modelMapper;
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
        UserDto.UserInfoRequest user = UserDto.UserInfoRequest.builder()
                .name(userId)
                .password("test1234")
                .userId(userId)
                .build();
        User createdUser = userService.Register(user);




        ArrayList<User> participants = new ArrayList<>();
        participants.add(createdUser);

        List<RoomDto.ParticipantInfo> targetUser = participants.stream().map((participant) -> {
            return modelMapper.map(participant, RoomDto.ParticipantInfo.class);
        }).collect(Collectors.toList());

        RoomDto.AddRequest request = RoomDto.AddRequest.builder()
                .roomName(roomName)
                .participants(targetUser)
                .build();

        Room room = roomService.CreateRoom(request);

        List<Room> createdRoom = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(userId)
                .build());

        for(int i =0; i< chattingCnt; ++i)
        {
            chatService.CreateChatMessage(ChatDto.ChatMessageCreateRequest.builder()
                            .roomId(createdRoom.getLast().getId())
                            .userId(userId)
                            .message(message)
                    .build());
        }

    }

    @Test
    @DisplayName("Pageing Chattings")
    public void PageChatting()
    {
        var user = userService.FindUserByID(userId);
        var rooms = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(user.getUserId())
                .build());

        int cnt = 0;
        var cursor = 99999999L;
        while (true)
        {
            var chattings = chatService.GetChatMessageByCursor(ChatDto.ChatMessageRequest.builder()
                    .cursor(cursor)
                    .roomId(rooms.getFirst().getId())
                    .build());

            for(var chat : chattings)
            {
                assertThat(chat.getMessage().contains(message)).isTrue();
                assertThat(chat.getRoomName()).isEqualTo(roomName);
            }

            if(chattings.isEmpty())
                break;

            cursor = chattings.getFirst().getId();
            cnt += chattings.size();
        }
        assertThat(cnt).isEqualTo(chattingCnt);
    }
}
