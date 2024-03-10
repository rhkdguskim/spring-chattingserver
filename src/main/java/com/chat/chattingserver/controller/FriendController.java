package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.aop.annotation.CurrentUserId;
import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.service.FriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Friend Controller", description = "Friend Controller API")
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final ModelMapper modelMapper;
    
    @GetMapping("")
    public ResponseEntity<CommonResponse> GetMyFriends(@CurrentUserId String userId)
    {
        FriendDto.Request request = FriendDto.Request.builder()
                .userId(userId)
                .build();
        var users = this.friendService.getFriends(request);
        var result = users.stream().map(user -> modelMapper.map(user, UserDto.UserInfo.class)).toList();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> AddFriend(@RequestBody FriendDto.AddRequest request, @CurrentUserId String userId) {

        var friend = friendService.addFriend(userId, request);
        var result = modelMapper.map(friend, FriendDto.AddResponse.class);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse> ModFriend(@RequestBody FriendDto.ModifyRequest request, @CurrentUserId String userId)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.modifyFriend(userId, request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse> DeleteFriend(@RequestBody FriendDto.DeleteRequest request, @CurrentUserId String userId)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.delFriend(userId, request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
