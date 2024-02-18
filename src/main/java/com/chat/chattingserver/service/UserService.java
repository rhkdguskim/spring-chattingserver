package com.chat.chattingserver.service;

import com.chat.chattingserver.common.exception.error.user.InvalidUseridException;
import com.chat.chattingserver.common.util.SecurityUtil;
import com.chat.chattingserver.dto.UserLoginDto;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserDto.Response> GetUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserDto.Response.builder()
                        .user_id(user.getUserid())
                        .status_msg(user.getStatusMessage())
                        .build())
                        .collect(Collectors.toList());
    }
    @Transactional
    public User Register(UserDto.Request userRegisterRequest)
    {
        if(userRepository.existsUserByUserid(userRegisterRequest.getUser_id()))
        {
            throw new InvalidUseridException("Already User Exist");
        }

        User user = new User();
        String UserID = userRegisterRequest.getUser_id();
        user.setUserid(userRegisterRequest.getUser_id());
        user.setNickname(userRegisterRequest.getName());
        user.setPassword(SecurityUtil.encryptSHA256(userRegisterRequest.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public UserDto.Response Login(UserLoginDto.Request userLoginRequest)
    {
        User user = findUserByID(userLoginRequest.getUser_id());

        if(!user.getPassword().equals(SecurityUtil.encryptSHA256(userLoginRequest.getPassword())))
        {
            throw new InvalidUseridException("InValid Password");
        }

        return UserDto.Response.builder()
                .user_id(user.getUserid())
                .role(user.getUserRole())
                .build();
    }

    @Transactional
    public UserDto.Response FindUserByID(String userid)
    {
        User user = findUserByID(userid);
        return UserDto.Response.builder()
                .name(user.getNickname())
                .user_id(user.getUserid())
                .build();
    }


    private User findUserByID(String userid)
    {
        Optional<User> userOptional = userRepository.findByUserid(userid);
        if(userOptional.isEmpty())
        {
            throw new InvalidUseridException("There is no User in Server");
        }

        return userOptional.get();
    }
}
