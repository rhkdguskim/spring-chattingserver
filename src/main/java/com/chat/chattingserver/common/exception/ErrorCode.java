package com.chat.chattingserver.common.exception;

import com.chat.chattingserver.common.exception.error.util.EncryptException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    ENCRYPT_EXCEPTION(HttpStatus.CONFLICT, "UTIL_001", "unsupported Algorithm or encrypt error"),

    AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH_001", "authentication Error"),
    AUTHORIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH_002", "authorization Error"),

    USER_ALREADY_EXCEPTION(HttpStatus.CONFLICT, "USER_001", "User Already Existed"),
    USER_INVALID_EXCEPTION(HttpStatus.CONFLICT, "USER_002", "Invalid UserID"),
    USER_PASSWORD_EXCEPTION(HttpStatus.CONFLICT, "USER_003", "Password Error");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}