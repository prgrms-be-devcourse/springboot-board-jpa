package com.example.springbootboard.service;

import com.example.springbootboard.entity.User;
import com.example.springbootboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository jpaUserRepository;

    public UserService(UserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public Optional<User> findById(Long id){
        return jpaUserRepository.findById(id);
    }

    public List<User> findAll(){
        return jpaUserRepository.findAll();
    }
}
