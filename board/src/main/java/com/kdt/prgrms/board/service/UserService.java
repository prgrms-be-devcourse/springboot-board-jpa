package com.kdt.prgrms.board.service;

import com.kdt.prgrms.board.entity.user.User;
import com.kdt.prgrms.board.entity.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException();
        }

        userRepository.save(user);
    }
}
