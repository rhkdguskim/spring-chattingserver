package com.chat.chattingserver.common.util;

import com.chat.chattingserver.common.exception.error.auth.AuthException;
import com.chat.chattingserver.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static String secretKey; //설정파일의 토큰 Key
    private static Long accessTokenValidTime; //access 토큰 유효시간
    private static Long refreshTokenValidTime; //refresh 토큰 유효시간

    /**
     * static 변수에 @value로 값을 주입하기 위해 setter 사용
     */
    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }
    @Value("${jwt.accesstoken.expiration}")
    public void setAccessTokenValidTime(Long accessTokenValidTime) {
        JwtUtil.accessTokenValidTime = accessTokenValidTime;
    }
    @Value("${jwt.refreshtoken.expireation}")
    public void setRefreshTokenValidTime(Long refreshTokenValidTime) {
        JwtUtil.refreshTokenValidTime = refreshTokenValidTime;
    }

    public static TokenDto generate(String encryptedUserId, String userRole) {

        //Header 설정
        Map<String, Object> headers = new HashMap<>();

        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", encryptedUserId);
        payloads.put("userRole", userRole);

        Date accessTokenExpirationDate = new Date(System.currentTimeMillis() + accessTokenValidTime * 1000L); //엑세스 토큰 만료 시간
        Date refreshTokenExpirationDate = new Date(System.currentTimeMillis() + refreshTokenValidTime * 1000L); //리프레시 토큰 만료 시간

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        //accessToken 생성
        String accessToken = Jwts.builder()
                .setHeader(headers) //Headers 설정
                .setClaims(payloads) //Claims 설정
                .setSubject("accessToken") //토큰 용도
                .setExpiration(accessTokenExpirationDate) //토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //refreshToken 생성
        String refreshToken = Jwts.builder()
                .setHeader(headers) //Headers 설정
                .setClaims(payloads) //Claims 설정
                .setSubject("refreshToken") //토큰 용도
                .setExpiration(refreshTokenExpirationDate) //토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static Authentication validate(String token){
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            if (claims.get("userRole") == null || claims.get("userId") == null) {
                throw new AuthException(AuthException.ErrorCode.INVALID_TOKEN);
            }

            // 클레임에서 권한 정보 가져오기
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("userRole").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            // UserDetails 객체를 만들어서 Authentication 리턴
            UserDetails principal = new User(SecurityUtil.decryptAES256(claims.get("userId").toString()), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new AuthException(AuthException.ErrorCode.UNSIGN_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthException.ErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new AuthException(AuthException.ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            throw new AuthException(AuthException.ErrorCode.ILLARGUMENT_JWT);
        }
    }

    public static String getEncryptedUserId(String token){
        String payload = token.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String decodedPayload = new String(decoder.decode(payload));
        JsonParser jsonParser = new BasicJsonParser();
        Map<String,Object> jsonArray = jsonParser.parseMap(decodedPayload);

        if(!jsonArray.containsKey("userId")){
            throw new AuthException(AuthException.ErrorCode.INVALID_TOKEN);
        }

        return jsonArray.get("userId").toString();
    }
}