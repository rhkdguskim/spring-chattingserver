package com.chat.chattingserver.common.exception.error.user;

import com.chat.chattingserver.common.exception.AbstactException;
import com.chat.chattingserver.common.exception.error.ErrorCodeInterface;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class UserException extends AbstactException {
    public enum ErrorCode implements ErrorCodeInterface
    {
        USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 사용자 입니다."),
        USER_INVALID(HttpStatus.CONFLICT, "알 수 없는 유저입니다."),
        USER_WRONG_PASSWORD(HttpStatus.CONFLICT, "비밀번호를 확인해주세요."),
        USER_NO_FOUNED(HttpStatus.CONFLICT, "유저를 찾을 수 없습니다.");

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

    public UserException(ErrorCode code) {
        super(code);
    }

    public UserException(ErrorCode code, String extraMessage) {
        super(code, extraMessage);
    }
}
