package com.chat.chattingserver.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_001", "authentication Error"),
    INVALID_USER_ID_EXCEPTION(HttpStatus.NOT_FOUND, "USER_001", "Invalid Userid");
    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}