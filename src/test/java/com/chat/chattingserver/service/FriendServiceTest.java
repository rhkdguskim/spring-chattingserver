package com.chat.chattingserver.service;


import java.util.List;

import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FriendServiceTest {
    private final static int  USER_CNT = 10;

    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    @BeforeAll
    public void addUsers()
    {
        for (int i = 0; i < USER_CNT; i ++)
        {
            UserDto.UserInfoRequest user = UserDto.UserInfoRequest.builder()
                    .name("test_user_account" + i)
                    .password("test_user_name" + i)
                    .userId("test_user_password" + i)
                    .build();
            userService.register(user);
        }
    }

    @Test
    @DisplayName("addFriend")
    public void addFriend()
    {
        List<User> user = userService.getUsers();
        for(var user1 : user)
        {
            for(var user2 : user)
            {
                if (!user1.getUserId().equals(user2.getUserId()))
                {
                    FriendDto.AddRequest request = FriendDto.AddRequest.builder().
                            friendId(user1.getId()).
                            friendName(user1.getName()).
                            build();

                    friendService.addFriend(user2.getUserId(), request);
                }
            }
        }
    }

    @Test
    @DisplayName("getFriends")
    public void getFriends()
    {
        addFriend();

        List<User> user = userService.getUsers();
        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getUserId()).build();
            List<User> users = friendService.getFriends(request);
            assertThat(users.size()).isEqualTo(USER_CNT-1);
        });
    }

    @Test
    @DisplayName("delFriends")
    public void delFriends()
    {
        List<User> user = userService.getUsers();

        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getUserId()).build();
            List<User> users = friendService.getFriends(request);

            users.forEach(friend -> {
                FriendDto.DeleteRequest delRequest = FriendDto.DeleteRequest.builder()
                        .friendId(friend.getId())
                        .build();
                friendService.delFriend(u1.getUserId(), delRequest);
            });

        });
    }

    @Test
    @DisplayName("modFriends")
    public void modFriends()
    {

        List<User> user = userService.getUsers();

        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getUserId()).build();
            List<User> users = friendService.getFriends(request);

            users.forEach(friend -> {
                FriendDto.ModifyRequest modRequest = FriendDto.ModifyRequest.builder()
                        .friendId(friend.getId())
                        .friendName(friend.getName() + "mod")
                        .build();
                assertThat(friendService.modifyFriend(u1.getUserId(), modRequest)).isEqualTo(true);
            });

        });
    }
}
