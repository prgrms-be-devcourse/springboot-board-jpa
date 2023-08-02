package com.programmers.springbootboardjpa.domain.user.service;

import com.programmers.springbootboardjpa.domain.user.domain.User;
import com.programmers.springbootboardjpa.domain.user.domain.UserRepository;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = new User(userRequestDto.name(), userRequestDto.age(), userRequestDto.hobby());
        User savedUser = userRepository.save(user);

        return UserResponseDto.from(savedUser);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        user.update(userRequestDto);

        return UserResponseDto.from(user);
    }

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::from)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
    }

    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponseDto::from);
    }
}
