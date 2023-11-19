package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserDto.Response save(UserDto.Request request) {
        User user = User.toEntity(request);
        return UserDto.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserDto.Response> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserDto.Response findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return UserDto.toResponse(user);
    }

    public void update(Long postId, UserDto.Request request) {
        User user = userRepository.findById(postId).orElseThrow(NoSuchElementException::new);
        user.changeUserInfo(request.name(), request.age(), request.hobby());
    }
}
