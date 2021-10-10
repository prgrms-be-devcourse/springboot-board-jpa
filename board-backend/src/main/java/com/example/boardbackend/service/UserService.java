package com.example.boardbackend.service;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.dto.converter.UserConverter;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public List<UserDto> findUserAll(){
        return userRepository.findAll().stream()
                .map(userEntity -> userConverter.convertToDto(userEntity))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        User user = userConverter.convertToEntity(userDto);
        User saved = userRepository.save(user);
        return userConverter.convertToDto(saved);
    }

}
