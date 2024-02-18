package com.chat.chattingserver.common.exception.error.user;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super();
    }
    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserAlreadyExistException(String message) {
        super(message);
    }
    public UserAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
