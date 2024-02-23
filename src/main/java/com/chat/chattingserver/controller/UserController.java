package com.chat.chattingserver.controller;
import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@Slf4j
@Tag(name = "User Controller", description = "User Controller API")
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    UserController(UserService userService)
    {
        this.userService = userService;
    }
    @GetMapping("/")
    public ResponseEntity<CommonResponse> GetAllUsers() {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userService.GetUsers())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> Register(@RequestBody UserDto.Request userRegisterRequest) {


        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userService.Register(userRegisterRequest))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<CommonResponse> FindUserByID(@RequestParam(name = "user_id") String id)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userService.FindUserByID(id))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
