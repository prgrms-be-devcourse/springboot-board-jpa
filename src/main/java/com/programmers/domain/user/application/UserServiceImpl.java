package com.programmers.domain.user.application;

import com.programmers.domain.user.application.converter.UserConverter;
import com.programmers.domain.user.entity.User;
import com.programmers.domain.user.infra.UserRepository;
import com.programmers.domain.user.ui.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private static final String USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public Long createUser(UserDto userDto) {
        User user = userConverter.convertUser(userDto);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserDto findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));
        return userConverter.convertUserDto(user);
    }
}
