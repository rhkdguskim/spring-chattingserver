package com.chat.chattingserver.service;


import com.chat.chattingserver.common.aop.annotation.RoomType;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class RoomServiceTest {
    private final static String userId = "TestUser";
    private final static String userId2 = "TestUser2";
    private final static String roomName = "TestRoom";

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    @BeforeAll()
    public void init()
    {
        UserDto.Request user = UserDto.Request.builder()
                .name("TestUser")
                .password("test1234")
                .userId(userId)
                .build();

        UserDto.Request user2 = UserDto.Request.builder()
                .name("TestUser2")
                .password("test12345")
                .userId(userId2)
                .build();

        userService.Register(user);
        userService.Register(user2);
    }

    @Test
    @DisplayName("createRoom")
    public void CreateRoom()
    {
        List<UserDto.Response> users = userService.GetUsers();
        ArrayList<UserDto.Response> participants = new ArrayList<>();

        for(var user : users)
        {
            participants.add(UserDto.Response.builder()
                            .id(user.getId())
                            .userId(user.getUserId())
                            .name(user.getName())
                    .build());
        }

        RoomDto.AddRequest request = RoomDto.AddRequest.builder()
                .roomName(roomName)
                .participants(participants)
                .build();

        RoomDto.AddResponse room = roomService.CreateRoom(request);

        assertThat(room.getRoomName()).isEqualTo(roomName);
        assertThat(room.getRoomType()).isEqualTo(RoomType.FRIEND);
        assertThat(room.getParticipants().size()).isEqualTo(2);


        List<RoomDto.RoomResponse> createdRoom = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(userId)
                .build());
    }

    @Test
    @DisplayName("FindRoom")
    public void FindRoom()
    {
        List<RoomDto.RoomResponse> createdRoom = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(userId)
                .build());

        assertThat(createdRoom.getFirst().getRoomName()).isEqualTo(roomName);
        assertThat(createdRoom.getFirst().getRoomType()).isEqualTo(RoomType.FRIEND);
        assertThat(createdRoom.getFirst().getParticipants().size()).isEqualTo(2);

        List<RoomDto.RoomResponse> createdRoom2 = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(userId2)
                .build());

        assertThat(createdRoom2.getFirst().getRoomName()).isEqualTo(roomName);
        assertThat(createdRoom2.getFirst().getRoomType()).isEqualTo(RoomType.FRIEND);
        assertThat(createdRoom2.getFirst().getParticipants().size()).isEqualTo(2);
    }

}
