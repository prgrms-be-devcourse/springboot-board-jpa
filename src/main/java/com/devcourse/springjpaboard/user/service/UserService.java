package com.devcourse.springjpaboard.user.service;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.user.converter.UserConverter;
import com.devcourse.springjpaboard.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.springjpaboard.exception.ExceptionMessage.NOT_FOUND_USER;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserResponse save(CreateUserRequest createUserRequest) {
        User user = userConverter.convertUserRequest(createUserRequest);
        User entity = userRepository.save(user);
        return userConverter.convertUserResponse(entity);
    }

    public UserResponse findById(Long id) {
        return userRepository.findById(id).
                map(userConverter::convertUserResponse)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

}
