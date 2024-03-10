package com.chat.chattingserver.common.exception;

import com.chat.chattingserver.common.exception.error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public abstract class AbstactException extends RuntimeException {
    private final ErrorCodeInterface code;
    private String extraMessage = "";

    public AbstactException(ErrorCodeInterface code)
    {
        this.code = code;
    }

    public AbstactException(ErrorCodeInterface code, String extraMessage)
    {
        this.code = code;
        this.extraMessage = extraMessage;
    }

    @Override
    public final String getMessage()
    {
        if(!extraMessage.isEmpty())
        {
            return code.getMessage() + "reason:" + extraMessage;
        }
        else {
            return code.getMessage();
        }
    }

    public final HttpStatus getStatus() {
        return code.getStatus();
    }

    public final String getCode()
    {
        return code.getCode();
    }
}
