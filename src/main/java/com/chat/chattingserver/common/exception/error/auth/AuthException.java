package com.chat.chattingserver.common.exception.error.auth;

import com.chat.chattingserver.common.exception.AbstactException;
import com.chat.chattingserver.common.exception.error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public class AuthException extends AbstactException {
    public enum ErrorCode implements ErrorCodeInterface
    {
        AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),
        AUTH_UNSIGN_TOKEN(HttpStatus.UNAUTHORIZED, "서명되지 않는 토큰입니다."),
        AUTH_EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
        AUTH_UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰입니다."),
        AUTH_ILLARGUMENT_JWT(HttpStatus.UNAUTHORIZED, "잘못된 토큰 입력입니다.");

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

    public AuthException(ErrorCode code) {
        super(code);
    }

    public AuthException(ErrorCode code, String extraMessage) {
        super(code, extraMessage);
    }
}
