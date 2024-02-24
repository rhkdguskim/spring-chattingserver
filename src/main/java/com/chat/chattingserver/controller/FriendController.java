package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.aop.annotation.CurrentUserId;
import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.service.FriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name="Friend Controller", description = "Friend Controller API")
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    @GetMapping("")
    public ResponseEntity<CommonResponse> GetMyFriends(@CurrentUserId String userId)
    {
        FriendDto.Request result = FriendDto.Request.builder()
                .userId(userId)
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(this.friendService.getFriends(result))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> AddFriend(@RequestBody FriendDto.AddRequest request, @CurrentUserId String userId) {

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.addFriend(userId, request))
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
