package org.prgms.springbootboardjpayu.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgms.springbootboardjpayu.domain.User;
import org.prgms.springbootboardjpayu.dto.request.CreateUserRequest;
import org.prgms.springbootboardjpayu.dto.response.UserResponse;
import org.prgms.springbootboardjpayu.repository.UserRepository;
import org.prgms.springbootboardjpayu.service.converter.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(@Valid CreateUserRequest request) {
        User user = UserConverter.toUser(request);
        return UserConverter.toUserResponse(userRepository.save(user));
    }

}
