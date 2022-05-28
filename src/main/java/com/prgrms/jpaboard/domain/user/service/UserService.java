package com.prgrms.jpaboard.domain.user.service;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.dto.UserRequestDto;
import com.prgrms.jpaboard.domain.user.util.UserValidator;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.prgrms.jpaboard.domain.user.util.UserValidator.*;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ResultDto createUser(UserRequestDto userRequestDto) {
        validateUserRequestDto(userRequestDto);

        User savedUser = userRepository.save(userRequestDto.toEntity());

        return ResultDto.createResult(savedUser.getId());
    }

    private void validateUserRequestDto(UserRequestDto userRequestDto) {
        String className = "UserRequestDto";

        validateName(className, userRequestDto.getName());
        validateAge(className, userRequestDto.getAge());
        validateHobby(className, userRequestDto.getHobby());
    }
}
