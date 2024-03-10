package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.RoomType;
import com.chat.chattingserver.domain.Participant;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.repository.ParticipantRepository;
import com.chat.chattingserver.repository.RoomRepository;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    public List<Room> getMyRooms(RoomDto.RoomRequest request)
    {
        return roomRepository.findRoomByUsers(request.getUserId());
    }

    public Room createRoom(RoomDto.AddRequest request)
    {
        Room room = new Room();
        room.setRoomName(request.getRoomName());

        // 친구채팅일 경우
        if (request.getParticipants().size() > 1)  {
            room.setType(RoomType.FRIEND);
        }

        List<Participant> participants = new ArrayList<>();
        room = this.roomRepository.save(room);

        for (RoomDto.ParticipantInfo user : request.getParticipants()) {
            Participant participant = new Participant();
            User participantUser = userRepository.findByUserId(user.getUserId()).orElse(null);
            participant.setRoom(room);
            participant.setUser(participantUser);
            participant.setRoomName(request.getRoomName());
            participants.add(participantRepository.save(participant));
        }
        room.setParticipants(participants);
        return room;
    }
}
