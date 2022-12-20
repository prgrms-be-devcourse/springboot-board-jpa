package com.ys.board.domain.user.service;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.user.DuplicateNameException;
import com.ys.board.domain.user.model.User;
import com.ys.board.domain.user.api.UserCreateRequest;
import com.ys.board.domain.user.api.UserCreateResponse;
import com.ys.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request) {
        validateDuplicateName(request.getName());

        User user = User.create(request.getName(), request.getAge(), request.getHobby());

        return new UserCreateResponse(userRepository.save(user).getId());
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(User.class, userId));
    }

    private void validateDuplicateName(String name) {
        if (userRepository.existsByName(name)) {
            throw new DuplicateNameException("이미 존재하는 이름입니다. by " + name);
        }
    }

}
