package com.chat.chattingserver.common.exception.error.util;

import com.chat.chattingserver.common.exception.error.ErrorCodeInterface;
import com.chat.chattingserver.common.exception.error.auth.AuthException;
import org.springframework.http.HttpStatus;

public class EncryptException extends RuntimeException implements ErrorCodeInterface {
    private final ErrorCode code;
    private String extraMessage;
    public enum ErrorCode implements ErrorCodeInterface
    {
        UTIL_UNSUPPORTED_ALGORITHM(HttpStatus.CONFLICT, "지원되지 않는 알고리즘입니다."),
        UTIL_ENCRYPT_ERROR(HttpStatus.CONFLICT, "암호화 에러입니다.");

        private final HttpStatus httpStatus;
        private final String message;

        ErrorCode(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getCode() {
            return toString();
        }

        @Override
        public HttpStatus getStatus() {
            return httpStatus;
        }
    }

    public EncryptException(ErrorCode code) {
        super();
        this.code = code;
    }

    public EncryptException(ErrorCode code, String extraMessage) {
        super(extraMessage);
        this.code = code;
        this.extraMessage = extraMessage;
    }

    @Override
    public String getMessage()
    {
        if(!extraMessage.isEmpty())
        {
            return code.getMessage() + "reason:" + extraMessage;
        }
        else {
            return code.getMessage();
        }
    }

    public HttpStatus getStatus() {
        return code.getStatus();
    }

    public String getCode()
    {
        return code.getCode();
    }
}