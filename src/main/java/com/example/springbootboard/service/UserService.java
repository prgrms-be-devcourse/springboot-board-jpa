package com.example.springbootboard.service;

import com.example.springbootboard.dto.Converter;
import com.example.springbootboard.dto.UserCreateRequest;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.exception.UserNotFoundException;
import com.example.springbootboard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springbootboard.dto.Converter.*;


@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User createUser(UserCreateRequest request){
        return repository.save(userCreateRequestToUser(request));
    }

    @Transactional(readOnly = true)
    public com.example.springbootboard.dto.UserDto findUserById(Long id) throws Exception{
        return repository.findById(id)
                .map(Converter::userToDto)
                .orElseThrow(UserNotFoundException::new);
    }
}
