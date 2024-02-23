package com.chat.chattingserver.service;


import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.chat.chattingserver.dto.TokenDto;
import com.chat.chattingserver.common.exception.error.auth.AuthenticationException;
import com.chat.chattingserver.common.util.JwtUtil;
import com.chat.chattingserver.common.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    public TokenDto generateToken(String accountId, UserRole userRole) {

        //accountId 암호화
        String encryptedUserId;
        try{
            encryptedUserId = SecurityUtil.encryptAES256(accountId);
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }

        //토큰 생성
        TokenDto token = JwtUtil.generate(encryptedUserId, userRole.name());

        //refreshToken 정보 Redis에 만료시간을 설정해서 저장 (key:encryptedUserId, value:refreshToken)
        //키에 이미 값이 있으면 덮어 쓴다
        //redisTemplate.opsForValue().set(encryptedUserId, token.getRefreshToken(), Duration.ofSeconds(refreshTokenValidTime));

        return token;
    }

    public TokenDto reissueToken(String refreshToken){

        //refreshToken 검증
        JwtUtil.validate(refreshToken);

        String encryptedUserIdByToken = JwtUtil.getEncryptedUserId(refreshToken);
        String userRoleByToken = JwtUtil.getUserRole(refreshToken);

//        String refreshTokenByRedis = redisTemplate.opsForValue().get(encryptedUserIdByToken);
//
//        // Redis 의 refreshToken 값과 비교
//        if(!refreshToken.equals(refreshTokenByRedis)){
//            throw new InvalidTokenException();
//        }

        //토큰 재 발급

        //토큰 생성
        TokenDto token = JwtUtil.generate(encryptedUserIdByToken, userRoleByToken);

        //refreshToken 정보 Redis에 만료시간을 설정해서 저장 (key:encryptedUserId, value:refreshToken)
        //키에 이미 값이 있으면 덮어 쓴다
        //redisTemplate.opsForValue().set(encryptedUserIdByToken, token.getRefreshToken(), Duration.ofSeconds(refreshTokenValidTime));

        return token;
    }
}
