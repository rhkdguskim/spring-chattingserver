package com.chat.chattingserver.common.util;

import java.security.*;
import com.chat.chattingserver.common.exception.error.EncryptException;

public class SecurityUtil {
    /**
     * SHA-256으로 암호화
     */
    public static String encryptSHA256(String text) throws Exception {
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
            throw new EncryptException("지원하지 않는 암호화 알고리즘 사용",e);
        }
    }
}
