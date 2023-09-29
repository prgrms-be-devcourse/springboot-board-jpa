package com.blackdog.springbootBoardJpa.domain.user.service;

import com.blackdog.springbootBoardJpa.domain.user.repository.UserRepository;
import com.blackdog.springbootBoardJpa.domain.user.service.converter.UserServiceConverter;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserCreateRequest;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import com.blackdog.springbootBoardJpa.global.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.blackdog.springbootBoardJpa.global.response.ErrorCode.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final UserServiceConverter converter;

    public UserService(
            final UserRepository repository,
            final UserServiceConverter converter
    ) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    public UserResponse saveUser(@Valid UserCreateRequest dto) {
        return converter.toResponse(
                repository.save(converter.toEntity(dto)));
    }

    @Transactional
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    public UserResponses findAllUsers(Pageable pageable) {
        return converter.toResponses(repository.findAll(pageable));
    }

    public UserResponse findUserById(Long id) {
        return converter.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER)));
    }

}
