package com.example.springbootboard.service;

import com.example.springbootboard.entity.User;
import com.example.springbootboard.repository.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final JpaUserRepository jpaUserRepository;

    public UserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public Optional<User> findById(Long id){
        return jpaUserRepository.findById(id);
    }

    public List<User> findAll(){
        return jpaUserRepository.findAll();
    }
}
