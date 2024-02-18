package com.chat.chattingserver.common.exception.error.user;

public class UserPasswordException extends RuntimeException {
    public UserPasswordException() {
        super();
    }
    public UserPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserPasswordException(String message) {
        super(message);
    }
    public UserPasswordException(Throwable cause) {
        super(cause);
    }
}
