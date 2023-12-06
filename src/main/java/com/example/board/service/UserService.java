package com.example.board.service;

import com.example.board.converter.UserConverter;
import com.example.board.domain.User;
import com.example.board.dto.request.user.SelfUpdateUserRequest;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.dto.response.UserSummaryResponse;
import com.example.board.exception.CustomException;
import com.example.board.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserSummaryResponse getUser(Long id) {
        final User user = getAvailableUser(id);
        return UserConverter.toUserSummaryResponse(user);
    }

    public void updateUser(Long id, SelfUpdateUserRequest requestDto) {
        final User user = getAvailableUser(id);
        user.update(requestDto.name(), requestDto.age());
    }

    public void deleteUser(Long id) {
        final User user = getAvailableUser(id);
        user.delete();
    }

    public User getAvailableUser(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ResponseStatus.USER_NOT_FOUND));
        if (user.isDeleted()) {
            throw new CustomException(ResponseStatus.ALREADY_DELETED_USER);
        }
        return user;
    }
}
