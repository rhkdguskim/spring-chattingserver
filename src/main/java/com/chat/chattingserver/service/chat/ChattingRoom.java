package com.chat.chattingserver.service.chat;

import com.chat.chattingserver.domain.ChatMessage;

import java.io.IOException;
import java.util.HashMap;

public class ChattingRoom {

    private final HashMap<String, ParticipantSession> participants;

    ChattingRoom()
    {
        this.participants = new HashMap<>();
    }
    public void addParticipant(String sessionID, ParticipantSession participantSession)
    {
        participants.put(sessionID, participantSession);
    }

    public void delParticipant(String sessionId)
    {
        participants.remove(sessionId);
    }

    public void onBroadCast(ChatMessage message)
    {
        for (String key : participants.keySet()) {
            try {
                participants.get(key).SendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
