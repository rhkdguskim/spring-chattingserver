package com.chat.chattingserver.service;

import com.chat.chattingserver.common.exception.error.auth.AuthenticationException;
import com.chat.chattingserver.common.util.JwtUtil;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.TokenDto;
import com.chat.chattingserver.dto.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    private final String userName = "testUser";

    @BeforeAll
    void setUp() {
        userService.Register(UserDto.UserInfoRequest.builder()
                        .name(userName)
                        .password(userName + "1234")
                        .userId(userName)
                .build());
    }

    @Test
    @DisplayName("generateToken")
    void signIn()
    {
        User user = userService.FindUserByID(userName);
        TokenDto tokenTest = authService.generateToken(user);
        assertThat(tokenTest.getAccessToken()).isInstanceOf(String.class);
        assertThat(tokenTest.getRefreshToken()).isInstanceOf(String.class);

        JwtUtil.validate(tokenTest.getAccessToken());

        assertThrows(AuthenticationException.class, () -> {
            JwtUtil.validate("accesstokenmocking!@#$%^&*(");
        });
    }

    @Test
    @DisplayName("generateToken then reissueToken token")
    void reissueToken()
    {
        User user = userService.FindUserByID(userName);
        TokenDto tokenTest = authService.generateToken(user);
        assertThat(tokenTest.getAccessToken()).isInstanceOf(String.class);
        assertThat(tokenTest.getRefreshToken()).isInstanceOf(String.class);

        TokenDto newTokenTest = authService.reissueToken(tokenTest.getRefreshToken());
        assertThat(newTokenTest.getAccessToken()).isInstanceOf(String.class);
        assertThat(newTokenTest.getRefreshToken()).isInstanceOf(String.class);


        assertThrows(AuthenticationException.class, () -> {
            authService.reissueToken("mockingrefreshToken^%$^45645645$");
        });
    }
}

