package com.example.boardbackend.service;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.dto.DtoConverter;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    public List<UserDto> findUserAll(){
        return userRepository.findAll().stream()
                .map(user -> dtoConverter.convertToUserDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        User user = dtoConverter.convertToUserEntity(userDto);
        User saved = userRepository.save(user);
        return dtoConverter.convertToUserDto(saved);
    }

}
