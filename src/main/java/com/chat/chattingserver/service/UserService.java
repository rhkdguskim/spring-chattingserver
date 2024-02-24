package com.chat.chattingserver.service;

import com.chat.chattingserver.common.exception.error.user.UserAlreadyExistException;
import com.chat.chattingserver.common.exception.error.user.UserInvalidException;
import com.chat.chattingserver.common.exception.error.user.UserPasswordException;
import com.chat.chattingserver.common.util.SecurityUtil;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<User> GetUsers() {
        return userRepository.findAll();
    }

    public User Register(UserDto.Request userRegisterRequest)
    {
        if(userRepository.existsUserByUserId(userRegisterRequest.getUserId()))
        {
            throw new UserAlreadyExistException();
        }

        User user = new User();
        user.setUserId(userRegisterRequest.getUserId());
        user.setName(userRegisterRequest.getName());
        user.setPassword(SecurityUtil.encryptSHA256(userRegisterRequest.getPassword()));
        return userRepository.save(user);
    }


    public User Login(UserDto.Login.Request userLoginRequest)
    {
        User user = findUserByID(userLoginRequest.getUserId());

        if(!user.getPassword().equals(SecurityUtil.encryptSHA256(userLoginRequest.getPassword())))
        {
            throw new UserPasswordException();
        }

        return user;
    }


    public User FindUserByID(String userid)
    {
        return findUserByID(userid);
    }


    private User findUserByID(String userid)
    {
        Optional<User> userOptional = userRepository.findByUserId(userid);
        if(userOptional.isEmpty())
        {
            throw new UserInvalidException();
        }

        return userOptional.get();
    }
}
