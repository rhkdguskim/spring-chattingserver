package com.chat.chattingserver.controller;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Room Controller", description = "Room Controller API")
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<CommonResponse> GetMyRooms(@AuthenticationPrincipal UserDetails userDetails)
    {
        RoomDto.RoomRequest request = RoomDto.RoomRequest.builder()
                .userId(userDetails.getUsername())
                .build();

        var rooms = this.roomService.getMyRooms(request);
        var result = rooms.stream().map(this::roomToRoomInfo).toList();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse> CreateRoom(@RequestBody RoomDto.AddRequest request, @AuthenticationPrincipal UserDetails userDetails)
    {
        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(roomToRoomInfo(this.roomService.createRoom(request)))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private RoomDto.RoomInfo roomToRoomInfo(Room room)
    {
        return modelMapper.map(room, RoomDto.RoomInfo.class);
    }
}
