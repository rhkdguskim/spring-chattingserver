package com.chat.chattingserver.common.exception.error.auth;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super();
    }
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
