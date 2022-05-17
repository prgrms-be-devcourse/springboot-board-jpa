package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.user.request.UserCreationRequest;
import com.programmers.springbootboardjpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 생성
     * @param request
     * @return 생성된 user id
     */
    @Transactional
    public Long saveUser(UserCreationRequest request) {
        User userEntity = request.toEntity();
        User savedUser = userRepository.save(userEntity);
        return savedUser.getId();
    }
}
