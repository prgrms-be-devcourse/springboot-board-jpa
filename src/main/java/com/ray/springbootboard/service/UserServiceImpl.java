package com.ray.springbootboard.service;

import com.ray.springbootboard.domain.User;
import com.ray.springbootboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long save(User user) {
        return userRepository.save(user).getId();
    }
}
