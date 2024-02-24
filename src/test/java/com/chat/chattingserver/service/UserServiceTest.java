package com.chat.chattingserver.service;


import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static String userId = "TestUser";
    private Logger logger = LoggerFactory.getLogger(UserServiceTest.class.getName());


    @Test
    @DisplayName("createUser")
    public void createUser()
    {
        UserDto.UserInfoRequest user = UserDto.UserInfoRequest.builder()
                .name("TestUser")
                .password("test1234")
                .userId(userId)
                .build();
        User createdUser = userService.Register(user);

        assertThat(createdUser).isNotNull();
    }

}
