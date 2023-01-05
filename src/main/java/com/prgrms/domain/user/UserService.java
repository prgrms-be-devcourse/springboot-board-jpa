package com.prgrms.domain.user;

import com.prgrms.dto.UserDto.LoginRequest;
import com.prgrms.dto.UserDto.Response;
import com.prgrms.dto.UserDto.UserCreateRequest;
import com.prgrms.global.error.ErrorCode;
import com.prgrms.global.exception.EmailDuplicateException;
import com.prgrms.global.exception.LoginFailException;
import com.prgrms.global.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Response insertUser(@Valid UserCreateRequest userDto) {

        checkEmailDuplicate(userDto.email());

        User save = userRepository.save(userDto.toUser());
        return new Response(save);
    }

    private void checkEmailDuplicate(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("이메일이 중복되었습니다", ErrorCode.INVALID_PARAMETER);
        }
    }

    @Transactional(readOnly = true)
    public Response findUserById(Long id) {

        return userRepository.findById(id)
            .map(Response::new)
            .orElseThrow(() -> new UserNotFoundException("id: " + id, ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Response login(@Valid LoginRequest loginDto) {

        return userRepository.findUser(loginDto.email(), loginDto.password())
            .map(Response::new)
            .orElseThrow(() -> new LoginFailException(ErrorCode.LOGIN_FAILED));
    }

}
