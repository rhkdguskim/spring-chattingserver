package com.chat.chattingserver.controller;

import com.chat.chattingserver.common.util.QueryParserUtil;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.WebSocketInterfaceChatDto;
import com.chat.chattingserver.service.*;
import com.chat.chattingserver.service.chatting.ChatMessageProcessor;
import com.chat.chattingserver.service.chatting.UserSession;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatMessageProcessor chatMessageProcessor;
    private final AuthService authService;
    private final Map<WebSocketSession, UserSession> sessions = new HashMap<>();

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
            var userSession = new WebSocketUserSession(session);
            sessions.put(session, userSession);
            chatMessageProcessor.onConnect(userSession, authService.validate(token));
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
                var userSession = sessions.get(session);
                chatMessageProcessor.onMessage(userSession, onMessage.getMessage(), onMessage.getRoomId());
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
        var userSession = sessions.get(session);
        chatMessageProcessor.onDisconnect(userSession);
        sessions.remove(session);
        log.info("{} Disconnected", session.getId());
    }

    /**
     * WebsocketSession 래퍼 클래스
     */
    private static class WebSocketUserSession implements UserSession {
        private final WebSocketSession session;

        public WebSocketUserSession(WebSocketSession session)
        {
            this.session = session;
        }

        @Override
        public void sendMessage(String message) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getId() {
            return session.getId();
        }
    }
}
