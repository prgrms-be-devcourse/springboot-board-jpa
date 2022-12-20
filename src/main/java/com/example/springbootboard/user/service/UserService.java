package com.example.springbootboard.user.service;

import com.example.springbootboard.user.repository.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


}
