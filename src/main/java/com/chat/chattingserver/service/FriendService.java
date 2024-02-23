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
                .role(user.getUserRole())
                .userId(user.getUserId())
                .id(user.getId())
                .statusMsg(user.getStatusMessage())
                .name(user.getName())
                .build()).toList();

        return FriendDto.Response
                .builder()
                .friends(friends)
                .build();
    }

    public FriendDto.Add.Response addFriend(FriendDto.Add.Request request)
    {
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(() -> new RuntimeException("user not exists"));

        Friend new_friend = new Friend();
        new_friend.setFriend_id(request.getFriendId());
        new_friend.setFriend_name(request.getFriendName());
        new_friend.setUser(user);
        new_friend = this.friendRepository.save(new_friend);
        return FriendDto.Add.Response.builder()
                .FriendId(new_friend.getFriend_id())
                .friendName(new_friend.getFriend_name())
                .build();
    }

    public boolean delFriend(FriendDto.Delete.Request request)
    {
        friendRepository.delete(request.getUserId(), request.getFriendId());
        return true;
    }

    public boolean modifyFriend(FriendDto.Modify.Request request)
    {
        Friend friend = friendRepository.findFriend(request.getUserId(), request.getFriendId()).orElseThrow(()-> new RuntimeException("no friend founded"));
        friend.setFriend_name(request.getFriendName());
        friendRepository.save(friend);
        return true;
    }


}
