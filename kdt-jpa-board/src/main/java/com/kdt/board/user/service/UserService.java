package com.kdt.board.user.service;

import com.kdt.board.common.exception.NotFoundException;
import com.kdt.board.user.converter.UserConverter;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.dto.request.UserCreateRequestDto;
import com.kdt.board.user.dto.response.UserResponseDto;
import com.kdt.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public Long save (UserCreateRequestDto userCreateRequestDto) {
        User user = userConverter.toUser(userCreateRequestDto);
        User entity = userRepository.save(user);
        return entity.getId();
    }

    @Transactional
    public UserResponseDto findOne(Long id) {
        return userRepository.findById(id)
            .map(order -> userConverter.toUserResponseDto(order))
            .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }

}
