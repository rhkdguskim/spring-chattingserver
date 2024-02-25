package com.chat.chattingserver.common.exception.error;

import org.springframework.http.HttpStatus;

public interface ExceptionInterface {
    public String getMessage();
    public String getCode();
    public HttpStatus getStatus();
}
