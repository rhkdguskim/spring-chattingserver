package com.chat.chattingserver.service;


import com.chat.chattingserver.domain.Friend;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.FriendRepository;
import com.chat.chattingserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public FriendDto.Response getFriends(FriendDto.Request request)
    {
        List<User> users = friendRepository.getUserFriends(request.getUserId()).orElseThrow(() -> new RuntimeException("no Friends founded"));

        List<UserDto.Response> friends = users.stream().map(user-> UserDto.Response.builder()
                        .name(user.getNickname())
                        .statusMsg(user.getStatusMessage())
                        .build()).toList();

        return FriendDto.Response
                .builder()
                .users(friends)
                .build();
    }

    public FriendDto.Add.Response addFriend(FriendDto.Add.Request request)
    {
        if(!userRepository.existsById(request.getUserId()))
        {
            throw new RuntimeException("user not exists");
        }

        User user = new User();
        user.setId(request.getUserId());

        Friend friend = new Friend();
        friend.setFriend_id(request.getFriendId());
        friend.setFriend_name(request.getFriendName());
        friend.setUser(user);
        friend = this.friendRepository.save(friend);
        return FriendDto.Add.Response.builder()
                .id(friend.getId())
                .friendName(friend.getFriend_name())
                .build();
    }

    public boolean delFriend(FriendDto.Delete.Request request)
    {
        friendRepository.deleteById(request.getId());
        return true;
    }

    public boolean modifyFriend(FriendDto.Modify.Request request)
    {
        com.chat.chattingserver.domain.Friend friend = friendRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("no friend founded"));
        friend.setFriend_name(request.getFriendName());
        friendRepository.save(friend);
        return true;
    }


}
