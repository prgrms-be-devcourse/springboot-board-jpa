package com.prgrms.jpaboard.domain.user.service;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ResultDto createUser(UserRequestDto userRequestDto) {
        User savedUser = userRepository.save(userRequestDto.toEntity());

        return ResultDto.createResult(savedUser.getId());
    }
}
