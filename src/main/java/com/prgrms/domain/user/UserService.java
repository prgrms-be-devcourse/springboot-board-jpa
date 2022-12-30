package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.*;

import com.prgrms.dto.UserDto.Response;
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

    public Response insertUser(@Valid Request userDto) {

        User savedUser = userRepository.save(userDto.toUser());
        return new Response(savedUser);
    }

    public Response findUserById(Long id) {

        return userRepository.findById(id)
            .map(Response::new)
            .orElseThrow(() -> new UserNotFoundException("id: " + id, ErrorCode.USER_NOT_FOUND));
    }

}
