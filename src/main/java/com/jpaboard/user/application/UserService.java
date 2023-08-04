package com.jpaboard.user.application;

import com.jpaboard.exception.NotFoundUserException;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.infra.JpaUserRepository;
import com.jpaboard.user.ui.UserConverter;
import com.jpaboard.user.ui.dto.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private static final String NOT_FOUND_USER_MESSAGE = "회원 정보를 찾을 수 없습니다.";

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long saveUser(User user) {
        User createdUser = userRepository.save(user);
        return createdUser.getId();
    }

    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .map(UserConverter::convertUserDto)
                .orElseThrow(() -> new NotFoundUserException(NOT_FOUND_USER_MESSAGE));
    }
}
