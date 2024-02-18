package com.chat.chattingserver.common.exception;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.common.dto.ErrorResponse;
import com.chat.chattingserver.common.exception.error.auth.AuthenticationException;
import com.chat.chattingserver.common.exception.error.auth.AuthorizationException;
import com.chat.chattingserver.common.exception.error.user.UserAlreadyExistException;
import com.chat.chattingserver.common.exception.error.user.UserInvalidException;
import com.chat.chattingserver.common.exception.error.user.UserPasswordException;
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
    protected ResponseEntity<CommonResponse> handleEncryptException(EncryptException ex) {
        ErrorCode errorCode = ErrorCode.ENCRYPT_EXCEPTION;

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

    @ExceptionHandler(AuthorizationException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(AuthorizationException ex) {
        ErrorCode errorCode = ErrorCode.AUTHORIZATION_EXCEPTION;

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

    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(UserAlreadyExistException ex) {
        ErrorCode errorCode = ErrorCode.USER_ALREADY_EXCEPTION;

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
    @ExceptionHandler(UserInvalidException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(UserInvalidException ex) {
        ErrorCode errorCode = ErrorCode.USER_INVALID_EXCEPTION;

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

    @ExceptionHandler(UserPasswordException.class)
    protected ResponseEntity<CommonResponse> handleEncryptException(UserPasswordException ex) {
        ErrorCode errorCode = ErrorCode.USER_PASSWORD_EXCEPTION;

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
