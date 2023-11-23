package com.devcourse.springbootboardjpahi.service;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreateUserRequest;
import com.devcourse.springbootboardjpahi.dto.UserResponse;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse create(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                .name(createUserRequest.name())
                .age(createUserRequest.age())
                .hobby(createUserRequest.hobby())
                .build();

        User savedUser = userRepository.save(newUser);

        return UserResponse.from(savedUser);
    }
}
