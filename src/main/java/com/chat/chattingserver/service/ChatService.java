package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.Chat;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.repository.ChatRepository;
import com.chat.chattingserver.repository.RoomRepository;
import com.chat.chattingserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatService {
    private final int MAX_CHATTING_CNT = 50;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(UserRepository userRepository, RoomRepository roomRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.chatRepository = chatRepository;
    }

    public ChatDto.ChatMessageResponse CreateChatMessage(ChatDto.ChatMessageCreateRequest request)
    {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("there is no User"));
        Room room = roomRepository.findById(request.getRoomId()).orElse(null);

        // Last Chatting Update
        room.setLastChat(request.getMessage());
        roomRepository.save(room);

        // Add Chatting
        Chat chat = new Chat();
        chat.setUser(user);
        chat.setRoom(room);
        chat.setMessage(request.getMessage());
        chat = chatRepository.save(chat);

        return ChatDto.ChatMessageResponse.builder()
                .id(chat.getId())
                .roomName(chat.getRoom().getRoomName())
                .userName(chat.getUser().getName())
                .message(chat.getMessage())
                .createdAt(chat.getCreatedAt())
                .updatedAt(chat.getUpdatedAt())
                .build();
    }

    public List<ChatDto.ChatMessageResponse> GetChatMessageByCursor(ChatDto.ChatMessageRequest request)
    {
        PageRequest page = PageRequest.of(0, MAX_CHATTING_CNT, Sort.by(Sort.Direction.DESC, "id"));
        return chatRepository.getChattings(request.getRoomId(), request.getCursor(), page).getContent();
    }

}
