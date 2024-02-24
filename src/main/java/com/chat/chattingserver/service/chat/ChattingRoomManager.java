package com.chat.chattingserver.service.chat;

import com.chat.chattingserver.domain.ChatMessage;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.SessionData;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.service.ChatService;
import com.chat.chattingserver.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChattingRoomManager {
    private final HashMap<Long, ChattingRoom> chattingRooms = new HashMap<>();
    private final HashMap<WebSocketSession, SessionData> sessions = new HashMap<>();
    private final RoomService roomService;
    private final ChatService chatService;

    public void onConnect(WebSocketSession session, User user)
    {
        List<RoomDto.RoomResponse> rooms = roomService.FindmyRoom(RoomDto.RoomRequest.builder()
                .userId(user.getUserId())
                .build());

        List<Room> roomList = rooms.stream().map((room) -> {
            Room new_room = new Room();
            new_room.setId(room.getRoomId());
            new_room.setRoomName(room.getRoomName());
            new_room.setLastChat(room.getLastChat());
            new_room.setType(room.getRoomType());
            new_room.setParticipants(room.getParticipants().stream().map((participant)-> {
                return new com.chat.chattingserver.domain.Participant();
            }).collect(Collectors.toList()));
            return new_room;
        }).collect(Collectors.toList());

        SessionData data = SessionData.builder()
                .user(user)
                .rooms(roomList)
                .build();

        sessions.put(session, data);

        ParticipantSession participantSession = new ParticipantSession(session, user.getUserId(), user.getName());

        for (Room room : roomList) {
            if (!chattingRooms.containsKey(room.getId())) {
                chattingRooms.put(room.getId(), new ChattingRoom());
                chattingRooms.get(room.getId()).addParticipant(session.getId(), participantSession);
            } else {
                chattingRooms.get(room.getId()).addParticipant(session.getId(), participantSession);
            }
        }
    }

    public void onDisconnect(WebSocketSession session)
    {
        for (Room room : sessions.get(session).getRooms()) {
            if (chattingRooms.containsKey(room.getId())) {
                chattingRooms.get(room.getId()).delParticipant(session.getId());
            }
        }

        sessions.remove(session);
    }

    public void onMessage(WebSocketSession session, String message, Long roomId)
    {
        User user = sessions.get(session).getUser();

        ChatMessage chatMessage = chatService.CreateChatMessage(ChatDto.ChatMessageCreateRequest.builder()
                        .userId(user.getUserId())
                        .message(message)
                        .roomId(roomId)
                        .build());

        if(chattingRooms.containsKey(roomId))
        {
            chattingRooms.get(roomId).onBroadCast(chatMessage);
        }
    }

}
