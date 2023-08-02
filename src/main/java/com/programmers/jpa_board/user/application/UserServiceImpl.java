package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.global.converter.BoardConverter;
import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.CreateUserResponse;
import com.programmers.jpa_board.user.infra.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserProviderService {
    private final UserRepository userRepository;
    private final BoardConverter converter;

    public UserServiceImpl(UserRepository userRepository, BoardConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
        User user = converter.toUser(request);
        User savedUser = userRepository.save(user);

        return converter.toDto(savedUser);
    }
}
