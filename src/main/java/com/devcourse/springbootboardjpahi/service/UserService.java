package com.devcourse.springbootboardjpahi.service;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.UserDto;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(UserDto userDto) {
        User user = User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .hobby(userDto.hobby())
                .build();

        return userRepository.save(user);
    }
}
