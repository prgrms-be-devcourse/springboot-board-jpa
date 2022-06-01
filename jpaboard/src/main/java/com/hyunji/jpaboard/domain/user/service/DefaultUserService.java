package com.hyunji.jpaboard.domain.user.service;

import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void register(User user) {
        userRepository.save(user);
    }
}
