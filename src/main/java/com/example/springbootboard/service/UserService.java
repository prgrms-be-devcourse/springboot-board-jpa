package com.example.springbootboard.service;

import com.example.springbootboard.converter.DtoConverter;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.dto.UserResponseDto;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.exception.error.NotFoundException;
import com.example.springbootboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConverter dtoConverter;

    public UserService(UserRepository userRepository, DtoConverter dtoConverter) {
        this.userRepository = userRepository;
        this.dtoConverter = dtoConverter;
    }

    @Transactional
    public Long insert(UserRequestDto userRequestDto) {
        User user = dtoConverter.convertUser(userRequestDto);
        User userEntity = userRepository.save(user);

        return userEntity.getId();
    }

    @Transactional
    public UserResponseDto findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(user -> dtoConverter.convertUserResponseDto(user))
                .orElseThrow(() -> {
                    throw new NotFoundException("유저를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> dtoConverter.convertUserResponseDto(user))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("유저를 찾을 수 없습니다.");
                });
        user.changeName(userRequestDto.getName());
        user.changeAge(userRequestDto.getAge());
        user.changeHobby(userRequestDto.getHobby());

        return dtoConverter.convertUserResponseDto(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
