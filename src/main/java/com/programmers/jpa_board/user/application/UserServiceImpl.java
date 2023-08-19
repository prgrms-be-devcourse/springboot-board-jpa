package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.UserDto;
import com.programmers.jpa_board.global.exception.NotFoundException;
import com.programmers.jpa_board.user.infra.UserRepository;
import com.programmers.jpa_board.user.util.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.programmers.jpa_board.global.exception.ExceptionMessage.NOT_FOUND_USER;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserProviderService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto.UserResponse save(UserDto.CreateUserRequest request) {
        User user = UserConverter.toEntity(request);
        User saved = userRepository.save(user);

        return UserConverter.toDto(saved);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER.getMessage()));
    }
}
