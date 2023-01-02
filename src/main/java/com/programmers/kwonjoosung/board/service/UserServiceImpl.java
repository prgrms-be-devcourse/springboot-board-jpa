package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.dto.CreateUserRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public IdResponse saveUser(CreateUserRequest request) {
        return new IdResponse(userRepository.save(request.toEntity()).getId());
    }
}
