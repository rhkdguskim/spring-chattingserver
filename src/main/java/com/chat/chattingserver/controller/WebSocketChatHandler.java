package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.util.QueryParserUtil;
import com.chat.chattingserver.dto.ChatMessageDto;
import com.chat.chattingserver.dto.ChatOnMessageDto;
import com.chat.chattingserver.service.AuthService;
import com.chat.chattingserver.service.chat.ChattingRoomManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    @Autowired
    private final ChattingRoomManager chattingRoomManager;
    private final AuthService authService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        URI uri = session.getUri();
        if (uri != null) {
            String query = uri.getQuery();
            Map<String, String> queryParams = QueryParserUtil.parseQueryParams(query);
            String token = queryParams.get("token");
            chattingRoomManager.onConnect(session, authService.validate(token));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessageDto<?> chatMessageDto = mapper.readValue(payload, new TypeReference<ChatMessageDto<?>>() {});

        log.info("chatType : {}", chatMessageDto.getType());

        switch (chatMessageDto.getType()) {
            case ONMESSAGE:
                ChatOnMessageDto onMessage = mapper.readValue(mapper.writeValueAsString(chatMessageDto.getPayload()), ChatOnMessageDto.class);
                log.info("onMessage ChatType : {}, Message: {}", onMessage.getType(), onMessage.getMessage());
                chattingRoomManager.onMessage(session, onMessage.getMessage(), onMessage.getRoomId());
                break;
            default:
            {
                session.sendMessage(new TextMessage("Unkown Message Received"));
                break;
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결끊김", session.getId());
        chattingRoomManager.onDisconnect(session);
    }
}
