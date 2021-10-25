package com.kdt.board.service;

import com.kdt.board.domain.User;
import com.kdt.board.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findOneUser(Long userId) {
        return userRepository.findById(userId);
    }
}
