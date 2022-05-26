package com.example.springbootboardjpa.user.service;

import com.example.springbootboardjpa.domain.User;
import com.example.springbootboardjpa.domain.UserRepository;
import com.example.springbootboardjpa.user.converter.UserConverter;
import com.example.springbootboardjpa.user.dto.CreateUserRequest;
import com.example.springbootboardjpa.user.dto.UserDto;
import com.example.springbootboardjpa.user.dto.UserResponse;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository,
                       UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public UserResponse insert(CreateUserRequest createUserRequest) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .hobby(createUserRequest.getHobby())
                .name(createUserRequest.getName())
                .age(createUserRequest.getAge())
                .build();
        User saved = userRepository.save(user);
        return userConverter.convertUserResponse(saved);
    }

    @Transactional
    public Page<UserResponse> findAll(Pageable pageable) {
        List<UserResponse> list = userRepository.findAll()
                .stream().map(userConverter::convertUserResponse).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    @Transactional
    public UserResponse findById(UUID id) throws NotFoundException {
        return userRepository.findById(id)
                .map(userConverter::convertUserResponse)
                .orElseThrow(() ->
                        new NotFoundException("유저를 찾을 수 없습니다."));
    }

    @Transactional
    public UserResponse update(UserDto userDto) {
        User user = userConverter.convertUser(userDto);
        User updated = userRepository.save(user);
        return userConverter.convertUserResponse(updated);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
