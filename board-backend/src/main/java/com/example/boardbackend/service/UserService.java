package com.example.boardbackend.service;

import com.example.boardbackend.converter.UserConverter;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDto> findUserAll(){
        return userRepository.findAll().stream()
                .map(userEntity -> userConverter.convertToUserDto(userEntity))
                .collect(Collectors.toList());
    }
}
