package com.waterfogsw.springbootboardjpa.user.service;

import com.waterfogsw.springbootboardjpa.common.exception.ResourceNotFoundException;
import com.waterfogsw.springbootboardjpa.user.entity.User;
import com.waterfogsw.springbootboardjpa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        Assert.notNull(user, "User should not be null");
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getOne(long id) {
        Assert.isTrue(id > 0, "User id should be positive");
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist"));
    }
}
