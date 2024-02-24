package com.chat.chattingserver.controller;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name="Room Controller", description = "Room Controller API")
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("")
    public ResponseEntity<CommonResponse> GetMyRooms(@AuthenticationPrincipal UserDetails userDetails)
    {
        RoomDto.RoomRequest request = RoomDto.RoomRequest.builder()
                .userId(userDetails.getUsername())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(this.roomService.FindmyRoom(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> CreateRoom(@RequestBody RoomDto.AddRequest request, @AuthenticationPrincipal UserDetails userDetails)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(this.roomService.CreateRoom(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
