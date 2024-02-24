package com.chat.chattingserver.service;


import com.chat.chattingserver.domain.Friend;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.FriendDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.FriendRepository;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    public List<User> getFriends(FriendDto.Request request)
    {
        return friendRepository.getUserFriends(request.getUserId()).orElseThrow(() -> new RuntimeException("no Friends founded"));
    }
    public Friend addFriend(String userId, FriendDto.AddRequest request)
    {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("user not exists"));

        Friend new_friend = new Friend();
        new_friend.setFriend_id(request.getFriendId());
        new_friend.setFriend_name(request.getFriendName());
        new_friend.setUser(user);
        return this.friendRepository.save(new_friend);
    }

    public boolean delFriend(String userId, FriendDto.DeleteRequest request)
    {
        friendRepository.delete(userId, request.getFriendId());
        return true;
    }

    public boolean modifyFriend( String userId, FriendDto.ModifyRequest request)
    {
        Friend friend = friendRepository.findFriend(userId, request.getFriendId()).orElseThrow(()-> new RuntimeException("no friend founded"));
        friend.setFriend_name(request.getFriendName());
        friendRepository.save(friend);
        return true;
    }


}
