package com.chat.chattingserver.service;

import com.chat.chattingserver.common.exception.error.user.UserAlreadyExistException;
import com.chat.chattingserver.common.exception.error.user.UserInvalidException;
import com.chat.chattingserver.common.exception.error.user.UserPasswordException;
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
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserDto.Response> GetUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserDto.Response.builder()
                        .user_id(user.getUserid())
                        .name(user.getNickname())
                        .status_msg(user.getStatusMessage())
                        .role(user.getUserRole())
                        .build())
                        .collect(Collectors.toList());
    }
    @Transactional
    public UserDto.Response Register(UserDto.Request userRegisterRequest)
    {
        if(userRepository.existsUserByUserid(userRegisterRequest.getUser_id()))
        {
            throw new UserAlreadyExistException();
        }

        User user = new User();
        user.setUserid(userRegisterRequest.getUser_id());
        user.setNickname(userRegisterRequest.getName());
        user.setPassword(SecurityUtil.encryptSHA256(userRegisterRequest.getPassword()));
        User newUser = userRepository.save(user);
        return UserDto.Response.builder()
                .user_id(newUser.getUserid())
                .name(newUser.getNickname())
                .status_msg(newUser.getStatusMessage())
                .role(newUser.getUserRole())
                .build();
    }

    @Transactional
    public UserDto.Response Login(UserLoginDto.Request userLoginRequest)
    {
        User user = findUserByID(userLoginRequest.getUser_id());

        if(!user.getPassword().equals(SecurityUtil.encryptSHA256(userLoginRequest.getPassword())))
        {
            throw new UserPasswordException();
        }

        return UserDto.Response.builder()
                .user_id(user.getUserid())
                .name(user.getNickname())
                .status_msg(user.getStatusMessage())
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
            throw new UserInvalidException();
        }

        return userOptional.get();
    }
}
