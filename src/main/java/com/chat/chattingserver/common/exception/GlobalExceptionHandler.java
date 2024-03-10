package com.chat.chattingserver.common.exception;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.common.dto.ErrorResponse;
import com.chat.chattingserver.common.exception.error.auth.AuthException;
import com.chat.chattingserver.common.exception.error.user.UserException;
import com.chat.chattingserver.common.exception.error.util.EncryptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EncryptException.class)
    protected ResponseEntity<CommonResponse> handleAuthException(EncryptException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .code(ex.getCode())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<CommonResponse> handleAuthException(AuthException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .code(ex.getCode())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<CommonResponse> handleUserException(UserException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .code(ex.getCode())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }
}
