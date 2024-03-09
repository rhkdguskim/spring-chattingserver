package com.chat.chattingserver.service;

import com.chat.chattingserver.common.util.JsonUtil;
import com.chat.chattingserver.domain.ChatMessage;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.SessionData;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final Map<Long, ChattingRoom> chattingRooms = new HashMap<>();
    private final Map<WebSocketSession, SessionData> sessions = new HashMap<>();
    private final RoomService roomService;
    private final ChatService chatService;

    public void onConnect(WebSocketSession session, User user)
    {
        SessionData data = addSession(session, user);
        joinRoom(data);
    }

    /**
     * 유저의 정보를 가지고 session 정보를 초기화한다.
     * @param session : Websocket Session
     * @param user : User 정보
     * @return : SessionData
     */
    private SessionData addSession(WebSocketSession session, User user)
    {
        List<Room> roomList = roomService.getMyRooms(RoomDto.RoomRequest.builder()
                .userId(user.getUserId())
                .build());

        SessionData data = SessionData.builder()
                .user(user)
                .rooms(roomList)
                .session(session)
                .build();
        sessions.put(session, data);
        return data;
    }

    /**
     * 해당 유저가 가지고 있는 모든방에대하여 채팅방에 참가하도록 한다.
     * @param data : session 정보
     */
    private void joinRoom(SessionData data)
    {
        ParticipantSession participantSession = new ParticipantSession(data.getSession(), data.getUser().getUserId(), data.getUser().getName());
        for (Room room : data.getRooms()) {
            if (!chattingRooms.containsKey(room.getId())) {
                // 채팅방이 존재하지 않다면 채팅방을 만든다.
                chattingRooms.put(room.getId(), new ChattingRoom());
                chattingRooms.get(room.getId()).addParticipant(participantSession.session.getId(), participantSession);
            } else {
                // 기존채팅방에 참가자를 추가한다.
                chattingRooms.get(room.getId()).addParticipant(participantSession.session.getId(), participantSession);
            }
        }
    }

    public void onDisconnect(WebSocketSession session)
    {
        var sessionData = sessions.get(session);
        leaveRoom(sessionData);
        removeSession(sessionData);
    }


    /**
     * 새션사용자가 가지고있는 모든방에 대하여 채팅방을 떠난다.
     * @param data : session 정보
     */
    private void leaveRoom(SessionData data)
    {
        for (Room room : data.getRooms()) {
            if (chattingRooms.containsKey(room.getId())) {
                chattingRooms.get(room.getId()).removeParticipant(data.getSession().getId());
            }
        }
    }

    /**
     * 새션을 삭제
     * @param data : session 정보
     */
    private void removeSession(SessionData data)
    {
        sessions.remove(data.getSession());
    }

    public void onMessage(WebSocketSession session, String message, Long roomId)
    {
        User user = sessions.get(session).getUser();

        ChatMessage chatMessage = chatService.createChatMessage(ChatDto.ChatMessageCreateRequest.builder()
                        .userId(user.getUserId())
                        .message(message)
                        .roomId(roomId)
                        .build());

        if(chattingRooms.containsKey(roomId))
        {
            chattingRooms.get(roomId).onBroadCast(chatMessage);
        }
    }

    @RequiredArgsConstructor
    private static class ChattingRoom {
        private final HashMap<String, ParticipantSession> participants = new HashMap<>();

        public void addParticipant(String sessionID, ParticipantSession participantSession)
        {
            participants.put(sessionID, participantSession);
        }

        public void removeParticipant(String sessionId)
        {
            participants.remove(sessionId);
        }

        public void onBroadCast(ChatMessage message)
        {
            for (String key : participants.keySet()) {
                try {
                    participants.get(key).sendMessage(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private record ParticipantSession(WebSocketSession session, String userId, String userName) {
        public void sendMessage(ChatMessage message) throws Exception {
            String stringMessage = JsonUtil.toJson(message);
            session.sendMessage(new TextMessage(stringMessage));
        }
    }
}
