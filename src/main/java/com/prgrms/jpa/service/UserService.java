package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.domain.User;
import com.prgrms.jpa.exception.EntityNotFoundException;
import com.prgrms.jpa.exception.ExceptionMessage;
import com.prgrms.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.UserEntityDtoMapper.toUser;
import static com.prgrms.jpa.utils.UserEntityDtoMapper.toUserIdDto;

@Service
public class UserService {

    private static final String USER = "사용자";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        User user = userRepository.save(toUser(createUserRequest));
        return toUserIdDto(user.getId());
    }

    @Transactional(readOnly = true)
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), USER)));
    }
}
