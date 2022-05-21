package com.example.jpaboard.service.user;

import com.example.jpaboard.domain.user.User;
import com.example.jpaboard.domain.user.UserRepository;
import com.example.jpaboard.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.jpaboard.exception.ErrorCode.INVALID_REQUEST;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Long save(String name, int age, String hobby) {
        if (name == null || name.isBlank()) {
            throw new CustomException(INVALID_REQUEST);
        }

        User user = userRepository.save(new User(name, age, hobby));
        return user.getId();
    }
}
