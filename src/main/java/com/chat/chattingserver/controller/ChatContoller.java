package com.chat.chattingserver.controller;


import com.chat.chattingserver.common.dto.CommonResponse;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Chat Controller", description = "Chat Controller API")
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatContoller {

    private final ChatService chatService;

    @GetMapping("")
    public ResponseEntity<CommonResponse> GetChattings(@RequestParam("roomId") Long roomId, @RequestParam("cursor") Long cursor, @AuthenticationPrincipal UserDetails userDetails)
    {
        ChatDto.ChatMessageRequest request = ChatDto.ChatMessageRequest.builder()
                .cursor(cursor)
                .roomId(roomId)
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(this.chatService.getChatMessageByCursor(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
