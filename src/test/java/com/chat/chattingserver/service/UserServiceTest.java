package com.chat.chattingserver.service;


import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("createUser")
    public void createUser()
    {
        UserDto.Request user = UserDto.Request.builder()
                .name("TestUser")
                .password("test1234")
                .userId("test_user")
                .build();
        UserDto.Response createdUser = userService.Register(user);

        assertThat(createdUser).isNotNull();
    }

}
