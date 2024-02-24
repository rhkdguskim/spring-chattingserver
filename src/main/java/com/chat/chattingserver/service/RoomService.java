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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public List<RoomDto.RoomResponse> FindmyRoom(RoomDto.RoomRequest request)
    {
        List<Room> room = roomRepository.findRoomByUsers(request.getUserId());
        return room.stream().map(r -> {
            List<UserDto.Response> users = r.getParticipants().stream().map(p -> UserDto.Response.builder()
                    .id(p.getUser().getId())
                    .name(p.getUser().getUsername())
                    .build()
            ).collect(Collectors.toList());

            return RoomDto.RoomResponse.builder()
                    .roomId(r.getId())
                    .roomName(r.getRoomName())
                    .lastChat(r.getLastChat())
                    .participants(users)
                    .roomType(r.getType())
                    .createdAt(r.getCreatedAt())
                    .updatedAt(r.getUpdatedAt())
                    .build();
        }).collect(Collectors.toList());

    }

    public RoomDto.AddResponse CreateRoom(RoomDto.AddRequest request)
    {
        Room room = new Room();
        ArrayList<Participant> participants = new ArrayList<>();
        room.setRoomName(request.getRoomName());
//        if (request.getParticipants().size() >= 2)  {
//            room.setType(RoomType.FRIEND);
//        }
        room = this.roomRepository.save(room);

        for (UserDto.Response user : request.getParticipants()) {
            Participant participant = new Participant();
            User participantUser = userRepository.findByUserId(user.getUserId()).orElse(null);
            participant.setRoom(room);
            participant.setUser(participantUser);
            participant.setRoomName(request.getRoomName());
            participants.add(participantRepository.save(participant));
        }

        List<UserDto.Response> users = participants.stream().map(p -> {
            return UserDto.Response.builder()
                    .id(p.getUser().getId())
                    .userId(p.getUser().getUserId())
                    .name(p.getUser().getUsername())
                    .build();
        }).collect(Collectors.toList());

        return RoomDto.AddResponse.builder()
                .roomId(room.getId())
                .roomName(room.getRoomName())
                .roomType(room.getType())
                .participants(users)
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }
}
