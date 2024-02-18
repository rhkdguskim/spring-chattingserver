package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.dto.UserLoginDto;
import com.chat.chattingserver.service.AuthService;
import com.chat.chattingserver.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Auth Controller", description = "Auth Controller API")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> Register(@RequestBody UserLoginDto.Request userloginRequest) {

        UserDto.Response userInfo = userService.Login(userloginRequest);
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(authService.generateToken(userInfo.getUser_id(), userInfo.getRole()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}