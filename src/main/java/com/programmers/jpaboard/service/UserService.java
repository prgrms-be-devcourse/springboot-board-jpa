package com.programmers.jpaboard.service;

import com.programmers.jpaboard.domain.User;
import com.programmers.jpaboard.dto.request.UserCreateRequest;
import com.programmers.jpaboard.dto.response.UserCreateResponse;
import com.programmers.jpaboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request) {
        User savedUser = userRepository.save(request.toEntity());

        return UserCreateResponse.of(savedUser.getId());
    }
}
