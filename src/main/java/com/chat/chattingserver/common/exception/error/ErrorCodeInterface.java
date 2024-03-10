package com.chat.chattingserver.common.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCodeInterface {
    public String getMessage();
    public String getCode();
    public HttpStatus getStatus();
}
