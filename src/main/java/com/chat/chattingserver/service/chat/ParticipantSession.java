package com.chat.chattingserver.service.chat;

import com.chat.chattingserver.common.util.JsonUtil;
import com.chat.chattingserver.domain.ChatMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Getter
public class ParticipantSession {
    private final WebSocketSession session;
    private final String userId;
    private final String userName;

    public void SendMessage(ChatMessage message) throws Exception {
        String stringMessage = JsonUtil.toJson(message);
        session.sendMessage(new TextMessage(stringMessage));
    }
}
