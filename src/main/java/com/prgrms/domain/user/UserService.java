package com.prgrms.domain.user;

import com.prgrms.dto.converter.UserConverter;
import com.prgrms.dto.UserDto;
import com.prgrms.exception.ErrorCode;
import com.prgrms.exception.customException.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto.Response insertUser(@Valid UserDto.Request userDto) {

        User savedUser = userRepository.save(userDto.toUser());
        return UserConverter.toUserResponseDto(savedUser);
    }

    public UserDto.Response findUserById(Long id) {

        return userRepository.findById(id)
            .map(UserConverter::toUserResponseDto)
            .orElseThrow(() -> new UserNotFoundException("id: " + id, ErrorCode.USER_NOT_FOUND));
    }

}
