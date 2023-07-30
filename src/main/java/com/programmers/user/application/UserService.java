package com.programmers.user.application;

import com.programmers.user.converter.UserConverter;
import com.programmers.user.domain.User;
import com.programmers.user.dto.UserDto;
import com.programmers.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public Long createUser(UserDto userDto) {
        User user = userConverter.convertUser(userDto);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public UserDto findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        return userConverter.convertUserDto(user);
    }
}
