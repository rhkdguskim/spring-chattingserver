package com.chat.chattingserver.common.config;

import com.chat.chattingserver.repository.*;
import com.chat.chattingserver.service.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ChattingApplicationConfig {

    @Bean
    public AuthService authService(UserRepository userRepository)
    {
        return new AuthService(userRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository)
    {
        return new UserService(userRepository);
    }

    @Bean
    public FriendService friendService(UserRepository userRepository, FriendRepository friendRepository)
    {
        return new FriendService(friendRepository, userRepository);
    }

    @Bean
    public RoomService roomService(ParticipantRepository participantRepository, RoomRepository roomRepository, UserRepository userRepository)
    {
        return new RoomService(roomRepository, participantRepository, userRepository);
    }

    @Bean
    public ChatService chatService(ModelMapper modelMapper, RoomRepository roomRepository, UserRepository userRepository, ChatRepository chatRepository)
    {
        return new ChatService(userRepository, roomRepository, chatRepository, modelMapper);
    }

}
