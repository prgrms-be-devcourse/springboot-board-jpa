package com.example.board.domain.user.service;

import com.example.board.domain.user.converter.UserConverter;
import com.example.board.domain.user.domain.User;
import com.example.board.domain.user.dto.UserDto;
import com.example.board.domain.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserConverter userConverter;
    private final UserRepository userRepository;

    public Long save(UserDto userDto) {
        User user = userConverter.convertUser(userDto);
        User save = userRepository.save(user);

        return save.getId();
    }

    public UserDto findOne(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(userConverter::convertUserDto)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }
}
