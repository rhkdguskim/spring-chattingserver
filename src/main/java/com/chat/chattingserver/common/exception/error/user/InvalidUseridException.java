package com.chat.chattingserver.common.exception.error.user;

public class InvalidUseridException extends RuntimeException {
    public InvalidUseridException() {
        super();
    }
    public InvalidUseridException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidUseridException(String message) {
        super(message);
    }
    public InvalidUseridException(Throwable cause) {
        super(cause);
    }
}
