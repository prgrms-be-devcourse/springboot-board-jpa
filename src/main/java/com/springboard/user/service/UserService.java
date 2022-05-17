package com.springboard.user.service;

import com.springboard.common.exception.FindFailException;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public FindUserResponse findOne(Long id) {
        User user = userRepository.findById(id).orElseThrow(FindFailException::new);
        return userConverter.getFindResponseFrom(user);
    }

    public Page<FindUserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userConverter::getFindResponseFrom);
    }

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = userRepository.save(userConverter.getUserFrom(request));
        return userConverter.getCreateResponseFrom(user);
    }

    @Transactional
    public FindUserResponse updateOne(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(FindFailException::new);
        user.setAge(request.age());
        user.setHobby(request.hobby());
        User response = userRepository.save(user);
        return userConverter.getFindResponseFrom(response);
    }

    @Transactional
    public void deleteOne(Long id) {
        userRepository.deleteById(id);
    }
}
