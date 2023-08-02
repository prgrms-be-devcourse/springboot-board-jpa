package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.global.converter.BoardConverter;
import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;
import com.programmers.jpa_board.user.exception.NotFoundUserException;
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
    public UserResponse create(CreateUserRequest request) {
        User user = converter.createUsertoUser(request);
        User saved = userRepository.save(user);

        return converter.userToDto(saved);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("회원이 존재하지 않습니다."));

        return converter.userToDto(user);
    }
}
