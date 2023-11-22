package com.example.board.service;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.CreateUserRequest;
import com.example.board.dto.request.UpdateUserRequest;
import com.example.board.dto.response.UserResponse;
import com.example.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(CreateUserRequest requestDto) {
        if (userRepository.existsByName(requestDto.name())) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
        return userRepository.save(UserConverter.toUser(requestDto)).getId();
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저가 존재하지 않습니다."));
        return UserConverter.toUserResponse(user);
    }

    public void updateUser(Long id, UpdateUserRequest requestDto) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저가 존재하지 않습니다."));
        user.update(requestDto.name(), requestDto.age(), requestDto.hobby());
    }

    public void deleteUser(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("유저가 존재하지 않습니다."));
        userRepository.delete(user);
    }
}
