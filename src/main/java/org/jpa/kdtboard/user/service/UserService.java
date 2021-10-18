package org.jpa.kdtboard.user.service;

import lombok.RequiredArgsConstructor;
import org.jpa.kdtboard.domain.board.User;
import org.jpa.kdtboard.domain.board.UserRepository;
import org.jpa.kdtboard.user.converter.UserConverter;
import org.jpa.kdtboard.user.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yunyun on 2021/10/14.
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public Long save(UserDto userDto) {
        User userSaved = userRepository.save(userConverter.convertEntity(userDto));
        return userSaved.getId();
    }

}
