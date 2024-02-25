package com.chat.chattingserver.service;


import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.TokenDto;
import com.chat.chattingserver.common.exception.error.auth.AuthException;
import com.chat.chattingserver.common.util.JwtUtil;
import com.chat.chattingserver.common.util.SecurityUtil;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public TokenDto generateToken(User user) throws AuthException {
        String encryptedUserId;
        encryptedUserId = SecurityUtil.encryptAES256(user.getUserId());
        return generate(encryptedUserId, user.getUserRole().toString());
    }

    public TokenDto reissueToken(String refreshToken) throws AuthException {
        User user = validate(refreshToken);
        String encryptedUserIdByToken = JwtUtil.getEncryptedUserId(refreshToken);
        return generate(encryptedUserIdByToken, user.getUserRole().toString());
    }

    public User validate(String token)
    {
        var authentication = JwtUtil.validate(token);
        return userRepository.findByUserId(authentication.getName()).orElseThrow(() -> new RuntimeException("no User"));
    }

    private TokenDto generate(String encryptedUserId, String userRole) throws AuthException
    {
        return JwtUtil.generate(encryptedUserId, userRole);
    }
}
