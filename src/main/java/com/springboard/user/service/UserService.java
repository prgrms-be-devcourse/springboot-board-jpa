package com.springboard.user.service;

import com.springboard.user.dto.CreateUserRequest;
import com.springboard.user.dto.CreateUserResponse;
import com.springboard.user.dto.FindUserResponse;
import com.springboard.user.dto.UpdateUserRequest;
import com.springboard.user.entity.User;
import com.springboard.user.repository.UserRepository;
import com.springboard.user.util.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = userRepository.save(UserConverter.getUserFrom(request));
        return UserConverter.getCreateResponseFrom(user);
    }

    public FindUserResponse findOne(Long id) {
        User user = userRepository.getById(id);
        return UserConverter.getFindResponseFrom(user);
    }

    public Page<FindUserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserConverter::getFindResponseFrom);
    }

    @Transactional
    public void deleteOne(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public FindUserResponse updateOne(Long id, UpdateUserRequest request) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(u -> {
            u.setAge(request.age());
            u.setHobby(request.hobby());
            userRepository.save(u);
        }, () -> {
            throw new RuntimeException();
        });
        return UserConverter.getFindResponseFrom(user.get());
    }
}
