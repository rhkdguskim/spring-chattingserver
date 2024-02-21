package com.chat.chattingserver.service;


import java.util.List;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.dto.UserDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class FriendServiceTest {
    private final static int  USER_CNT = 10;

    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    @BeforeEach
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
                    FriendDto.Add.Request request =
                            FriendDto.Add.Request.builder().userId(u2.getId()).friendId(u1.getId()).friendName(u1.getName()).build();
                    friendService.addFriend(request);
                }
            });
        });
    }

    @Test
    @DisplayName("getFriends")
    public void getFriends()
    {
        List<UserDto.Response> user = userService.GetUsers();
        user.forEach(u1 -> {
            FriendDto.Request request = FriendDto.Request.builder().userId(u1.getId()).build();
            FriendDto.Response response = friendService.getFriends(request);
        });
    }
}
