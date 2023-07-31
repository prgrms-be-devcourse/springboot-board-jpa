package com.programmers.board.service;

import com.programmers.board.domain.User;
import com.programmers.board.dto.UserDto;
import com.programmers.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createUser(String name, int age, String hobby) {
        User user = new User(name, age, hobby);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(UserDto::from);
    }

    @Transactional(readOnly = true)
    public UserDto findUser(Long userId) {
        User user = findUserOrElseThrow(userId);
        return UserDto.from(user);
    }

    @Transactional
    public void updateUser(Long userId, String name, Integer age, String hobby) {
        User user = findUserOrElseThrow(userId);
        user.update(name, age, hobby);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserOrElseThrow(userId);
        userRepository.delete(user);
    }

    private User findUserOrElseThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("일치하는 회원이 존재하지 않습니다."));
        return user;
    }
}
