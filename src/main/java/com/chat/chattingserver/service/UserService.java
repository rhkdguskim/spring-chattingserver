package com.chat.chattingserver.service;

import com.chat.chattingserver.common.exception.error.user.UserException;
import com.chat.chattingserver.common.util.SecurityUtil;
import com.chat.chattingserver.domain.User;
import com.chat.chattingserver.dto.UserDto;
import com.chat.chattingserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<User> GetUsers() {
        return userRepository.findAll();
    }

    public User Register(UserDto.UserInfoRequest userRegisterUserInfoRequest)
    {
        if(userRepository.existsUserByUserId(userRegisterUserInfoRequest.getUserId()))
        {
            throw new UserException(UserException.ErrorCode.USER_ALREADY_EXIST);
        }

        User user = new User();
        user.setUserId(userRegisterUserInfoRequest.getUserId());
        user.setName(userRegisterUserInfoRequest.getName());
        user.setPassword(SecurityUtil.encryptSHA256(userRegisterUserInfoRequest.getPassword()));
        return userRepository.save(user);
    }


    public User Login(UserDto.LoginRequest userLoginRequest)
    {
        User user = findUserByID(userLoginRequest.getUserId());

        if(!user.getPassword().equals(SecurityUtil.encryptSHA256(userLoginRequest.getPassword())))
        {
            throw new UserException(UserException.ErrorCode.WRONG_PASSWORD);
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
            throw new UserException(UserException.ErrorCode.NO_USER_FOUNED);
        }

        return userOptional.get();
    }
}
