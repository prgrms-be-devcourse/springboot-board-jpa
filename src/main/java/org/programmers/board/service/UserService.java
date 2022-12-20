package org.programmers.board.service;

import org.programmers.board.entity.User;
import org.programmers.board.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public Long createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}