package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Converter converter;

    @Transactional
    public Long insert(UserRequestDto dto) {
        User user = converter.toUserEntity(dto);
        userRepository.save(user);

        return user.getId(); // OSIV
    }

    @Transactional
    public void deleteOne(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("userId: " + id + " not found"));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(converter::toUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException("userId: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByName(String name) throws UserNotFoundException {
        return userRepository.findByName(name)
                .map(converter::toUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException("userName: " + name + " not found"));
    }
}
