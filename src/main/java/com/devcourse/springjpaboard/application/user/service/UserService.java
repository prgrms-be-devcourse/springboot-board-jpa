package com.devcourse.springjpaboard.application.user.service;

import com.devcourse.springjpaboard.core.exception.NotFoundException;
import com.devcourse.springjpaboard.application.user.model.User;
import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.application.user.converter.UserConverter;
import com.devcourse.springjpaboard.application.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_USER;

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
