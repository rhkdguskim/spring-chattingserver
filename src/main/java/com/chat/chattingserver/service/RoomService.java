package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.Participant;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.RoomDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.ParticipantRepository;
import com.chat.chattingserver.repository.RoomRepository;
import com.chat.chattingserver.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
private final RoomRepository roomRepository;
private final ParticipantRepository participantRepository;

private final UserRepository userRepository;
    public RoomService(ParticipantRepository participantRepository,RoomRepository roomRepository, UserRepository userRepository)
    {
        this.participantRepository = participantRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public List<RoomDto.RoomResponse> findRoom(RoomDto.RoomRequest request)
    {
        List<Room> room = roomRepository.findRoomByUsers(request.getUserId());
        return room.stream().map((r)-> RoomDto.RoomResponse
                        .builder()
                        .roomId(r.getId())
                        .roomName(r.getRoomName())
                        .lastChat(r.getLastChat())
                        .roomType(r.getType())
                        .build())
                .collect(Collectors.toList());

    }

    public RoomDto.AddResponse CreateRoom(RoomDto.AddRequest request)
    {

        Room room = new Room();
        ArrayList<Participant> participants = new ArrayList<>();
        room.setRoomName(request.getRoomName());
        room = this.roomRepository.save(room);

        for (UserDto.Response user : request.getParticipants()) {
            Participant participant = new Participant();
            User participantUser = userRepository.findByUserId(user.getUserId()).orElse(null);
            participant.setRoom(room);
            participant.setUser(participantUser);
            participant.setRoomName(request.getRoomName());
            participants.add(participantRepository.save(participant));
        }
        room.setParticipants(participants);
        room.setRoomName(request.getRoomName());


        return RoomDto.AddResponse.builder()
                .roomId(room.getId()).roomName(room.getRoomName()).build();
    }
}
