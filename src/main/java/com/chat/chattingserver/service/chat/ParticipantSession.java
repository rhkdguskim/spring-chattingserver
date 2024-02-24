package com.chat.chattingserver.service.chat;

import com.chat.chattingserver.common.util.JsonUtil;
import com.chat.chattingserver.domain.ChatMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@RequiredArgsConstructor
@Getter
public class ParticipantSession {
    private WebSocketSession session;
    private String userId;
    private String userName;

    ParticipantSession(WebSocketSession session, String userId, String userName)
    {
        this.session = session;
        this.userId = userId;
        this.userName = userName;
    }

    public void SendMessage(ChatMessage message) throws Exception {
        String stringMessage = JsonUtil.toJson(message);
        session.sendMessage(new TextMessage(stringMessage));
    }
}
