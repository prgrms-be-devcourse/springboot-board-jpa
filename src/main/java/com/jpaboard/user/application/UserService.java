package com.jpaboard.user.application;

import com.jpaboard.exception.NotFoundUserException;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.infra.JpaUserRepository;
import com.jpaboard.user.ui.UserConverter;
import com.jpaboard.user.ui.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long saveUser(User user) {
        User createdUser = userRepository.save(user);
        return createdUser.getId();
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .map(UserConverter::convertUserDto)
                .orElseThrow(NotFoundUserException::new);
    }
}
