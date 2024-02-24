package com.chat.chattingserver.service;

import com.chat.chattingserver.common.aop.annotation.RoomType;
import com.chat.chattingserver.domain.Participant;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.ParticipantRepository;
import com.chat.chattingserver.repository.RoomRepository;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    public List<Room> FindmyRoom(RoomDto.RoomRequest request)
    {
        return roomRepository.findRoomByUsers(request.getUserId());
    }

    public Room CreateRoom(RoomDto.AddRequest request)
    {
        Room room = new Room();
        ArrayList<Participant> participants = new ArrayList<>();

        room.setRoomName(request.getRoomName());

        if (request.getParticipants().size() >= 2)  {
            room.setType(RoomType.FRIEND);
        }

        room = this.roomRepository.save(room);

        for (UserDto.Response user : request.getParticipants()) {
            Participant participant = new Participant();
            User participantUser = userRepository.findByUserId(user.getUserId()).orElse(null);
            participant.setRoom(room);
            participant.setUser(participantUser);
            participant.setRoomName(request.getRoomName());
            participants.add(participantRepository.save(participant));
        }

        return room;
    }
}
