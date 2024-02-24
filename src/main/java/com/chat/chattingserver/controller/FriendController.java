package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.service.FriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name="Friend Controller", description = "Friend Controller API")
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    @GetMapping("")
    public ResponseEntity<CommonResponse> GetMyFriends(@AuthenticationPrincipal UserDetails userDetails)
    {
        FriendDto.Request result = FriendDto.Request.builder()
                .userId(userDetails.getUsername())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(this.friendService.getFriends(result))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> AddFriend(@RequestBody FriendDto.Add.Request request) {

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.addFriend(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse> ModFriend(@RequestBody FriendDto.Modify.Request request)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.modifyFriend(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<CommonResponse> DeleteFriend(@RequestBody FriendDto.Delete.Request request)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(friendService.delFriend(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
