package com.waterfogsw.springbootboardjpa.user.service;

import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        Assert.notNull(user, "User should not be null");
        userRepository.save(user);
    }
}
