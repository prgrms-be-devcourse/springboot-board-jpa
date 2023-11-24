package com.example.board.service;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.UserResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(CreateUserRequest requestDto) {
        validateUserName(requestDto.name());
        return userRepository.save(UserConverter.toUser(requestDto)).getId();
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        final User user = getAvailableUser(id);
        return UserConverter.toUserResponse(user);
    }

    public void updateUser(Long id, UpdateUserRequest requestDto) {
        final User user = getAvailableUser(id);
        user.update(requestDto.name(), requestDto.age(), requestDto.hobby());
    }

    public void deleteUser(Long id) {
        final User user = getAvailableUser(id);
        user.delete();
    }

    private void validateUserName(String name) {
        List<User> user = userRepository.findByNameAndDeletedAt(name, null);
        if (!user.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
        }
    }

    public User getAvailableUser(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.ALREADY_DELETED_USER);
        }
        return user;
    }
}
