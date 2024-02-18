package com.chat.chattingserver.common.exception;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.common.dto.ErrorResponse;
import com.chat.chattingserver.common.exception.error.auth.AuthenticationException;
import com.chat.chattingserver.common.exception.error.user.InvalidUseridException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(AuthenticationException ex) {
        ErrorCode errorCode = ErrorCode.AUTHENTICATION_EXCEPTION;

        ErrorResponse error = ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(InvalidUseridException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(InvalidUseridException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_USER_ID_EXCEPTION;

        ErrorResponse error = ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
