package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.util.QueryParserUtil;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.WebSocketInterfaceChatDto;
import com.chat.chattingserver.service.AuthService;
import com.chat.chattingserver.service.ChattingRoomService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ChattingRoomService chattingRoomService;
    private final AuthService authService;

    /**
     * 소켓 연결시
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} Established", session.getId());

        URI uri = session.getUri();

        if (uri != null) {
            String query = uri.getQuery();
            Map<String, String> queryParams = QueryParserUtil.parseQueryParams(query);

            String token = queryParams.get("token");
            chattingRoomService.onConnect(session, authService.validate(token));
            log.info("{} Connected", session.getId());
        }
        else {
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * 소켓 연결후 메세지 전송시, 추후 이미지, 동영상 전송등을 위한 분리구현
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        WebSocketInterfaceChatDto<?> chatWebSocketInterfaceDto = mapper.readValue(payload, new TypeReference<WebSocketInterfaceChatDto<?>>() {});

        log.info("chatType : {}", chatWebSocketInterfaceDto.getType());

        switch (chatWebSocketInterfaceDto.getType()) {
            case ONMESSAGE:
                ChatDto.ChatMessageCreateRequest onMessage = mapper.readValue(mapper.writeValueAsString(chatWebSocketInterfaceDto.getPayload()), ChatDto.ChatMessageCreateRequest.class);
                log.info("onMessage ChatType : {}, Message: {}", onMessage.getType(), onMessage.getMessage());
                chattingRoomService.onMessage(session, onMessage.getMessage(), onMessage.getRoomId());
                break;
            default:
            {
                session.sendMessage(new TextMessage("Unkown Message Received"));
                break;
            }
        }
    }

    /**
     * 연결 종료시에
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chattingRoomService.onDisconnect(session);
        log.info("{} Disconnected", session.getId());
    }
}
