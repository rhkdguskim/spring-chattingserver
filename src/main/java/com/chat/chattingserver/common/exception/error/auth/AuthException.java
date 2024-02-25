package com.chat.chattingserver.common.exception.error.auth;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    private final ErrorCode code;

    public enum ErrorCode
    {
        INVALID_TOKEN,
        UNSIGN_TOKEN,
        EXPIRED_JWT,
        UNSUPPORTED_JWT,
        ILLARGUMENT_JWT;
    }

    public AuthException(ErrorCode code) {
        super();
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return switch (code) {
            case INVALID_TOKEN -> "Invalid Token";
            case EXPIRED_JWT -> "Jwt Expired";
            case UNSUPPORTED_JWT -> "unSupport Jwt";
            case UNSIGN_TOKEN -> "unSigned Token";
            case ILLARGUMENT_JWT -> "Illagrument";
        };
    }

    public HttpStatus getStatus() {
        return switch (code) {
            case INVALID_TOKEN, EXPIRED_JWT,
                    UNSUPPORTED_JWT, UNSIGN_TOKEN,
                    ILLARGUMENT_JWT -> HttpStatus.UNAUTHORIZED;
        };
    }

    public String getCode()
    {
        return code.toString();
    }
}
