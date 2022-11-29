package com.springboard.user.service;

import com.springboard.common.exception.*;
import com.springboard.user.dto.*;
import com.springboard.user.entity.User;
import com.springboard.user.repository.UserRepository;
import com.springboard.user.util.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserResponse findOne(Long id) {
        User user = userRepository.findById(id).orElseThrow(FindFailException::new);
        return userConverter.getResponseFrom(user);
    }

    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userConverter::getResponseFrom);
    }

    @Transactional
    public UserResponse save(UserRequest request) {
        User user = userRepository.save(userConverter.getUserFrom(request));
        return userConverter.getResponseFrom(user);
    }

    @Transactional
    public UserResponse updateOne(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(FindFailException::new);
        user.setAge(request.age());
        user.setHobby(request.hobby());
        User response = userRepository.save(user);
        return userConverter.getResponseFrom(response);
    }

    @Transactional
    public void deleteOne(Long id) {
        userRepository.deleteById(id);
    }
}
