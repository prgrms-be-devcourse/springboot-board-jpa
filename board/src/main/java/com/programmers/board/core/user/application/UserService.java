package com.programmers.board.core.user.application;

import com.programmers.board.common.exception.NotFoundException;
import com.programmers.board.core.user.application.dto.UserCreateRequest;
import com.programmers.board.core.user.application.dto.UserResponse;
import com.programmers.board.core.user.domain.User;
import com.programmers.board.core.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse join(UserCreateRequest userCreateRequest){
        User user = userCreateRequest.toEntity();
        User savedUser = userRepository.save(user);
        return UserResponse.of(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id){
        return userRepository.findById(id)
                .map(UserResponse::of)
                .orElseThrow(NotFoundException::new);
    }

}
