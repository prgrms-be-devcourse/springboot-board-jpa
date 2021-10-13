package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Converter converter;

    @Transactional
    public Long insert(UserResponseDto userDto){
        User user = converter.toUserEntity(userDto);
        userRepository.save(user);

        return user.getId(); // OSIV
    }

    @Transactional
    public void delete(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("user not found"));

        user.delete();
        userRepository.delete(user);
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(converter::toUserResponseDto)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }
}
