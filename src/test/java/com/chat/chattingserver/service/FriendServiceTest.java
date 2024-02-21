package com.chat.chattingserver.service;


import java.util.List;
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
            UserDto.Request user = UserDto.Request.builder()
                    .name("test_user_account" + i)
                    .password("test_user_name" + i)
                    .userId("test_user_password" + i)
                    .build();
            userService.Register(user);
        }
    }

    @Test
    @DisplayName("addFriend")
    public void addFriend()
    {
        List<UserDto.Response> user = userService.GetUsers();
        user.forEach(u1 -> {
            user.forEach(u2 -> {
                if (!u1.getUserId().equals(u2.getUserId()))
                {
                    FriendDto.Add.Request request = FriendDto.Add.Request.builder().
                            userId(u2.getId()).
                            friendId(u1.getId()).
                            friendName(u1.getName()).
                            build();

                    friendService.addFriend(request);
                }
            });
        });
    }

    @Test
    @DisplayName("getFriends")
    public void getFriends()
    {
        addFriend();

        List<UserDto.Response> user = userService.GetUsers();
        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getId()).build();
            FriendDto.Response response = friendService.getFriends(request);
            assertThat(response.getUsers().size()).isEqualTo(USER_CNT-1);
        });
    }

    @Test
    @DisplayName("delFriends")
    public void delFriends()
    {
        List<UserDto.Response> user = userService.GetUsers();

        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getId()).build();
            FriendDto.Response response = friendService.getFriends(request);

            response.getUsers().forEach(friend -> {
                FriendDto.Delete.Request delRequest = FriendDto.Delete.Request.builder()
                        .userId(u1.getId())
                        .friendId(friend.getId())
                        .build();
                friendService.delFriend(delRequest);
            });

        });
    }

    @Test
    @DisplayName("modFriends")
    public void modFriends()
    {

        List<UserDto.Response> user = userService.GetUsers();

        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getId()).build();
            FriendDto.Response response = friendService.getFriends(request);

            response.getUsers().forEach(friend -> {
                FriendDto.Modify.Request modRequest = FriendDto.Modify.Request.builder()
                        .friendId(friend.getId())
                        .userId(u1.getId())
                        .friendName(friend.getName() + "mod")
                        .build();
                assertThat(friendService.modifyFriend(modRequest)).isEqualTo(true);
            });

        });
    }
}
