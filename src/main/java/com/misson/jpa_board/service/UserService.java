package com.misson.jpa_board.service;

import com.misson.jpa_board.converter.UserConverter;
import com.misson.jpa_board.domain.User;
import com.misson.jpa_board.dto.UserDto;
import com.misson.jpa_board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        User user = userRepository.save(userConverter.convertUser(userDto));
        user.setInfo(user.getId());
        return userConverter.convertUserDto(user);
    }
}
