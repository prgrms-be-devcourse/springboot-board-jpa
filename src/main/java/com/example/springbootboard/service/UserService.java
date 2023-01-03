package com.example.springbootboard.service;

import com.example.springbootboard.dto.Converter;
import com.example.springbootboard.dto.UserDto;
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
    public User createUser(UserDto dto){
        return repository.save(dtoToUser(dto));
    }

    @Transactional(readOnly = true)
    public UserDto findUserById(Long id) throws Exception{
        return repository.findById(id)
                .map(Converter::userToDto)
                .orElseThrow(UserNotFoundException::new);
    }
}
