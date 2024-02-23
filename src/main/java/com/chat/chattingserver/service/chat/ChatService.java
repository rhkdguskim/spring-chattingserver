package com.chat.chattingserver.service.chat;

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
import java.util.List;

@Service
public class ChatService {

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
                .build();
    }

    public List<ChatDto.ChatMessageResponse> GetChatMessageByCursor(ChatDto.ChatMessageRequest request)
    {
        PageRequest page = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "id"));
        return chatRepository.getChattings(request.getRoomId(), request.getCursor(), page).getContent();
    };


}
