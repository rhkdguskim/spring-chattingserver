package com.chat.chattingserver.service;

import com.chat.chattingserver.domain.Chat;
import com.chat.chattingserver.domain.BroadCastMessage;
import com.chat.chattingserver.domain.Room;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.ChatDto;
import com.chat.chattingserver.repository.ChatRepository;
import com.chat.chattingserver.repository.RoomRepository;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    public BroadCastMessage.ChatMessage createChatMessage(ChatDto.ChatMessageCreateRequest request)
    {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("there is no User"));
        Room room = roomRepository.findById(request.getRoomId()).orElse(null);

        // Last Chatting Update
        Objects.requireNonNull(room).setLastChat(request.getMessage());
        roomRepository.save(room);

        // Add Chatting
        Chat chat = new Chat();
        chat.setUser(user);
        chat.setRoom(room);
        chat.setMessage(request.getMessage());
        chat = chatRepository.save(chat);
        return modelMapper.map(chat, BroadCastMessage.ChatMessage.class);
    }

    public List<BroadCastMessage> getChatMessageByCursor(ChatDto.ChatMessageRequest request)
    {
        final int MAX_CHATTING_CNT = 50;
        PageRequest page = PageRequest.of(0, MAX_CHATTING_CNT, Sort.by(Sort.Direction.DESC, "id"));
        return chatRepository.getChattings(request.getRoomId(), request.getCursor(), page).getContent().reversed();
    }

}
