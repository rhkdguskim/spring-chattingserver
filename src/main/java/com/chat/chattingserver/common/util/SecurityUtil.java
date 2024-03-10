package com.chat.chattingserver.common.util;


import com.chat.chattingserver.common.exception.error.util.EncryptException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

@Component
public class SecurityUtil {

    /**
     * static 메서드에서 접근할 수 있도록 static 변수로 사용
     */
    private static String secretKey;
    private static String iv;

    /**
     * static 변수에 @Value어노테이션이 적용 되도록하는 setter
     */
    @Value("${jwt.payload.key}")
    public void setSecretKey(String secretKey) {
        SecurityUtil.secretKey = secretKey;
    }
    @Value("${jwt.payload.iv}")
    public void setIv(String iv) {
        SecurityUtil.iv = iv;
    }

    /**
     * SHA-256으로 암호화
     */
    public static String encryptSHA256(String text) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            byte[] bytes = md.digest();
            StringBuilder builder = new StringBuilder();
            for(byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(EncryptException.ErrorCode.UTIL_UNSUPPORTED_ALGORITHM);
        }
    }

    /**
     * AES-256으로 암호화
     * 토큰 payload의 UserId 암호화에 사용
     * 복호화는 API Gateway에 연결된 Authorizer에서 진행
     */
    public static String encryptAES256(String text) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(secretKey.substring(0,32).getBytes(), "AES"),
                    new IvParameterSpec(iv.substring(0,16).getBytes()));
            return new String(Base64.getEncoder().encode(cipher.doFinal(text.getBytes("UTF-8"))));
        } catch(Exception e) {
            throw new EncryptException(EncryptException.ErrorCode.UTIL_UNSUPPORTED_ALGORITHM);
        }
    }

    public static String decryptAES256(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(secretKey.substring(0, 32).getBytes(), "AES"),
                    new IvParameterSpec(iv.substring(0, 16).getBytes()));
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes("UTF-8")));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new EncryptException(EncryptException.ErrorCode.UTIL_ENCRYPT_ERROR);
        }
    }
}