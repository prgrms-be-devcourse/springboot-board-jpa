package com.programmers.board.service;

import com.programmers.board.domain.User;
import com.programmers.board.dto.UserDto;
import com.programmers.board.exception.AuthorizationException;
import com.programmers.board.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createUser(String name, int age, String hobby) {
        checkUserNameDuplication(name);
        User user = new User(name, age, hobby);
        userRepository.save(user);
        return user.getId();
    }

    private void checkUserNameDuplication(String name) {
        userRepository.findByName(name)
                .ifPresent(user -> {
                    throw new DuplicateKeyException("중복된 회원 이름입니다");
                });
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
    public void updateUser(Long loginUserId, Long userId, String name, Integer age, String hobby) {
        User user = findUserOrElseThrow(userId);
        checkAuthority(loginUserId, userId);
        user.update(name, age, hobby);
    }

    @Transactional
    public void deleteUser(Long userId, Long loginUserId) {
        User user = findUserOrElseThrow(userId);
        checkAuthority(loginUserId, userId);
        userRepository.delete(user);
    }

    private User findUserOrElseThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("일치하는 회원이 존재하지 않습니다."));
        return user;
    }

    private void checkAuthority(Long loginUserId, Long userId) {
        if (notEqualsUserId(loginUserId, userId)) {
            throw new AuthorizationException("권한이 없습니다");
        }
    }

    private boolean notEqualsUserId(Long loginUserId, Long userId) {
        return !Objects.equals(loginUserId, userId);
    }
}
