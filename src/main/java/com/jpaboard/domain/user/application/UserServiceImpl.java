package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.UserConverter;
import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long createUser(UserCreationRequest request) {
        User user = UserConverter.convertRequestToEntity(request);
        return userRepository.save(user).getId();
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return UserConverter.convertEntityToResponse(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        User forUpdate = UserConverter.convertRequestToEntity(request);
        user.update(forUpdate);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
