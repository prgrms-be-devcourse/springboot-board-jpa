package com.example.boardbackend.service;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.domain.embeded.Email;
import com.example.boardbackend.common.converter.DtoConverter;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.common.error.exception.NotFoundException;
import com.example.boardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        // Email 중복체크
        userRepository.findByEmail(new Email(userDto.getEmail()))
                .orElseThrow(() -> new NotFoundException("이미 중복되는 이메일이 있습니다"));
        User user = dtoConverter.convertToUserEntity(userDto);
        User saved = userRepository.save(user);
        return dtoConverter.convertToUserDto(saved);
    }

    public List<UserDto> findUsersAll() {
        return userRepository.findAll().stream()
                .map(dtoConverter::convertToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserByEmail(String email) {
        return userRepository.findByEmail(new Email(email))
                .map(dtoConverter::convertToUserDto)
                .orElseThrow(() -> new NotFoundException("가입되지 않은 이메일입니다."));
    }

    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(dtoConverter::convertToUserDto)
                .orElseThrow(() -> new NotFoundException("해당 ID의 사용자가 없음"));
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
