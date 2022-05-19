package com.prgrms.springboard.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.UserRepository;
import com.prgrms.springboard.user.dto.CreateUserRequest;
import com.prgrms.springboard.user.dto.UserResponse;
import com.prgrms.springboard.user.exception.UserNotFoundException;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(CreateUserRequest userRequest) {
        User user = userRepository.save(userRequest.toEntity());
        return user.getId();
    }

    @Transactional(readOnly = true)
    public UserResponse findOne(Long id) {
        return userRepository.findById(id)
            .map(UserResponse::from)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
