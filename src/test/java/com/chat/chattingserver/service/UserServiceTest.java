package com.chat.chattingserver.service;


import com.chat.chattingserver.common.exception.error.user.UserException;
import com.chat.chattingserver.domain.User;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private final static String userId = "TestUser";
    private final static String userName = "TestUser";
    private final static String userPassword = "test1234";

    @BeforeAll
    public void init()
    {
        UserDto.UserInfoRequest user = UserDto.UserInfoRequest.builder()
                .name(userName)
                .password(userPassword)
                .userId(userId)
                .build();

        userService.register(user);
    }

    @Test
    @DisplayName("createUser")
    public void createUser()
    {
        UserDto.UserInfoRequest user = UserDto.UserInfoRequest.builder()
                .name(userName + "test")
                .password(userPassword + "test")
                .userId(userId + "test")
                .build();

        User createdUser = userService.register(user);

        assertThat(createdUser.getUserId()).isEqualTo(userId + "test");
        assertThat(createdUser.getUsername()).isEqualTo(userName + "test");

        UserDto.UserInfoRequest user2 = UserDto.UserInfoRequest.builder()
                .name(userName)
                .password(userPassword)
                .userId(userId)
                .build();

        try {
            userService.register(user2);
            fail();
        } catch (UserException e)
        {
            assertThat(e.getCode()).isEqualTo(UserException.ErrorCode.USER_ALREADY_EXIST.toString());
        }
    }

    @Test
    @DisplayName("login")
    public void login()
    {
        User user = userService.login(UserDto.LoginRequest.builder()
                        .userId(userId)
                        .password(userPassword)
                        .build());

        assertThat(user.getUsername()).isEqualTo(userName);
        assertThat(user.getUserId()).isEqualTo(userId);

        try {
            userService.login(UserDto.LoginRequest.builder()
                    .userId(userId)
                    .password(userPassword + "1")
                    .build());
            fail();
        } catch (UserException e)
        {
            assertThat(e.getCode()).isEqualTo(UserException.ErrorCode.USER_WRONG_PASSWORD.toString());
        }
    }

    @Test
    @DisplayName("findById")
    public void findByID()
    {
        User user = userService.findUserByID(userId);

        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getUsername()).isEqualTo(userName);

        try {
            userService.findUserByID(userId + "test");
            fail();
        } catch (UserException e) {
            assertThat(e.getCode()).isEqualTo(UserException.ErrorCode.USER_NO_FOUNED.toString());
        }
    }
}
