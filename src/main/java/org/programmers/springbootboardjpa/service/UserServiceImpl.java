package org.programmers.springbootboardjpa.service;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootboardjpa.controller.dto.UserCreateForm;
import org.programmers.springbootboardjpa.domain.User;
import org.programmers.springbootboardjpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    @Override
    public Long registerUser(UserCreateForm userCreateForm) {
        return userRepository.save(userCreateForm.convertToUser()).getUserId();
    }

    @Override
    public User findUserWith(Long userId) {
        return null;
    }
}
