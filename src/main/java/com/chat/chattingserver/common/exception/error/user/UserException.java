package com.chat.chattingserver.common.exception.error.user;

import com.chat.chattingserver.common.exception.error.ExceptionInterface;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException implements ExceptionInterface {
    private final ErrorCode code;

    public enum ErrorCode
    {
        USER_ALREADY_EXIST,
        INVALID_USER,
        WRONG_PASSWORD,
        NO_USER_FOUNED,
    }

    public UserException(ErrorCode code) {
        super();
        this.code = code;
    }

    @Override
    public String getMessage() {
        return switch (code) {
            case USER_ALREADY_EXIST -> "User Already Exist";
            case INVALID_USER -> "Invalid User";
            case WRONG_PASSWORD -> "Wrong Password";
            case NO_USER_FOUNED -> "No User Founed";
        };
    }

    @Override
    public String getCode() {
        return code.toString();
    }

    @Override
    public HttpStatus getStatus() {
        return switch (code) {
            case USER_ALREADY_EXIST, INVALID_USER,
                    WRONG_PASSWORD, NO_USER_FOUNED -> HttpStatus.CONFLICT;
        };
    }
}
