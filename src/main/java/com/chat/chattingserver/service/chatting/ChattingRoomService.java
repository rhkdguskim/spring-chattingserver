package com.chat.chattingserver.service.chatting;

import com.chat.chattingserver.common.util.JsonUtil;
import com.chat.chattingserver.domain.*;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.service.ChatService;
import com.chat.chattingserver.service.RoomService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChattingRoomService implements ChatMessageProcessor {
    private final Map<Long, ChattingRoom> chattingRooms = new HashMap<>();
    private final Map<UserSession, SessionData> sessions = new HashMap<>();
    private final RoomService roomService;
    private final ChatService chatService;

    @Override
    public void onConnect(UserSession session, User user)
    {
        SessionData sessionData = addSession(session, user);
        joinRoom(sessionData);
        var enterMessage = BroadCastMessage.EnterMessage.builder()
                .id(sessionData.getUser().getId())
                .userName(sessionData.getUser().getName())
                .createdAt(Instant.now())
                .build();

        for (Room room : sessionData.getRooms()) {
            broadCastMessage(room.getId(), enterMessage);
        }
    }

    /**
     * 유저의 정보를 가지고 session 정보를 초기화한다.
     * @param session : user Session
     * @param user : User 정보
     * @return : SessionData
     */
    private SessionData addSession(UserSession session, User user)
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
        RoomParticipant roomParticipant = new RoomParticipant(data.getSession(), data.getUser().getUserId(), data.getUser().getName());
        for (Room room : data.getRooms()) {
            if (!chattingRooms.containsKey(room.getId())) {
                // 채팅방이 존재하지 않다면 채팅방을 만든다.
                chattingRooms.put(room.getId(), new ChattingRoom());
                chattingRooms.get(room.getId()).addParticipant(roomParticipant.session.getId(), roomParticipant);
            } else {
                // 기존채팅방에 참가자를 추가한다.
                chattingRooms.get(room.getId()).addParticipant(roomParticipant.session.getId(), roomParticipant);
            }
        }
    }

    @Override
    public void onDisconnect(UserSession session)
    {
        var sessionData = sessions.get(session);

        var leaveMessage = BroadCastMessage.LeaveMessage.builder()
                .id(sessionData.getUser().getId())
                .userName(sessionData.getUser().getName())
                .createdAt(Instant.now())
                .build();

        for (Room room : sessionData.getRooms()) {
            broadCastMessage(room.getId(), leaveMessage);
        }

        leaveRoom(sessionData);
        removeSession(session);
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
     * @param session : session
     */
    private void removeSession(UserSession session)
    {
        sessions.remove(session);
    }

    @Override
    public void onMessage(UserSession session, String message, Long roomId)
    {
        User user = sessions.get(session).getUser();

        BroadCastMessage.ChatMessage broadCastMessage = chatService.createChatMessage(ChatDto.ChatMessageCreateRequest.builder()
                        .userId(user.getUserId())
                        .message(message)
                        .roomId(roomId)
                        .build());

        broadCastMessage(roomId, broadCastMessage);
    }

    private <T extends AbstractBroadCastMessage> void broadCastMessage(Long roomId, T message)
    {
        if(chattingRooms.containsKey(roomId)) {
            chattingRooms.get(roomId).onBroadCast(message);
        }
        else {
            throw new RuntimeException("There is no room");
        }
    }

    @RequiredArgsConstructor
    private static class ChattingRoom {
        private final Map<String, RoomParticipant> participants = new HashMap<>();

        public void addParticipant(String sessionID, RoomParticipant roomParticipant)
        {
            participants.put(sessionID, roomParticipant);
        }

        public void removeParticipant(String sessionId)
        {
            participants.remove(sessionId);
        }

        public <T extends AbstractBroadCastMessage> void onBroadCast(T message)
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

    private record RoomParticipant(UserSession session, String userId, String userName) {
        public <T extends AbstractBroadCastMessage> void sendMessage(T message) throws Exception {
            String stringMessage = JsonUtil.toJson(message);
            session.sendMessage(stringMessage);
        }
    }
}
