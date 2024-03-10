package com.chat.chattingserver.controller;
import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@Tag(name = "User Controller", description = "User Controller API")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public ResponseEntity<CommonResponse> GetAllUsers() {
        var users = userService.getUsers();
        var result = users.stream().map(this::userToUserInfo).toList();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> Register(@RequestBody UserDto.UserInfoRequest userRegisterUserInfoRequest) {
        var user = userService.register(userRegisterUserInfoRequest);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userToUserInfo(user))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/find")
    public ResponseEntity<CommonResponse> FindUserByID(@RequestParam(name = "user_id") String id)
    {
        var user = userService.findUserByID(id);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userToUserInfo(user))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private UserDto.UserInfo userToUserInfo(User user)
    {
        return modelMapper.map(user, UserDto.UserInfo.class);
    }
}
