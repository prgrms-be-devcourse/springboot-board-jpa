package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.UserIdResponse;
import com.prgrms.jpa.domain.User;
import com.prgrms.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.ToDtoMapper.toUserIdDto;
import static com.prgrms.jpa.utils.ToEntityMapper.toUser;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserIdResponse create(CreateUserRequest createUserRequest) {
        User user = userRepository.save(toUser(createUserRequest));
        return toUserIdDto(user.getId());
    }
}
