package com.chat.chattingserver.common.exception.error.util;

import com.chat.chattingserver.common.exception.error.ExceptionInterface;
import org.springframework.http.HttpStatus;

public class EncryptException extends RuntimeException implements ExceptionInterface {
    private final ErrorCode code;

    public enum ErrorCode
    {
        UNSUPPORTED_ALGORITHM,
        ENCRYPT_ERROR,
    }

    public EncryptException(ErrorCode code) {
        super();
        this.code = code;
    }

    @Override
    public String getMessage()
    {
        return switch (code) {
            case UNSUPPORTED_ALGORITHM -> "Unsupported Algorithm";
            case ENCRYPT_ERROR -> "Encrypt Errror";
        };
    }

    public HttpStatus getStatus() {
        return switch (code) {
            case UNSUPPORTED_ALGORITHM, ENCRYPT_ERROR -> HttpStatus.CONFLICT;
        };
    }

    public String getCode()
    {
        return code.toString();
    }
}