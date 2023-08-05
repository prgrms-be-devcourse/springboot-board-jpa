package com.prgrms.board.service;

import com.prgrms.board.domain.Users;
import com.prgrms.board.dto.user.UserResponse;
import com.prgrms.board.dto.user.UserSaveRequest;
import com.prgrms.board.dto.user.UserUpdateRequest;
import com.prgrms.board.global.exception.custom.DuplicateEmailException;
import com.prgrms.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserSaveRequest saveRequest) {
        checkDuplicateEmail(saveRequest.getEmail());
        Users user = userRepository.save(
            new Users(saveRequest.getEmail(), saveRequest.getName(), saveRequest.getAge())
        );
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserResponse updateUser(UserUpdateRequest updateRequest, Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        user.updateUser(updateRequest.getName(), updateRequest.getAge());
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
