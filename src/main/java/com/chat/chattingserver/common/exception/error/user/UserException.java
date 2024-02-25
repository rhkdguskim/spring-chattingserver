package com.chat.chattingserver.common.exception.error.user;

import com.chat.chattingserver.common.exception.error.ExceptionInterface;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class UserException extends RuntimeException implements ExceptionInterface {
    private final ErrorCode code;
    private String userId;
    private String password;

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
            case USER_ALREADY_EXIST -> userId + "is already Exist";
            case INVALID_USER -> userId + " is invalid user";
            case WRONG_PASSWORD -> password + "is wrong password";
            case NO_USER_FOUNED -> "There in no" + userId + "id";
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
