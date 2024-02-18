package com.chat.chattingserver.service;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.chat.chattingserver.common.exception.error.auth.AuthenticationException;
import com.chat.chattingserver.common.util.JwtUtil;
import com.chat.chattingserver.dto.TokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthServiceTest {
    AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    @DisplayName("generateToken")
    void signIn()
    {
        TokenDto tokenTest = authService.generateToken("testuser", UserRole.NORMAL);
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
        TokenDto tokenTest = authService.generateToken("testuser", UserRole.NORMAL);
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

