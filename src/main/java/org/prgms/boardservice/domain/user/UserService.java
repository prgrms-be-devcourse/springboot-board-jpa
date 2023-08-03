package org.prgms.boardservice.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.prgms.boardservice.util.ErrorMessage.NOT_FOUND_USER;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(User user) {
        return userRepository.save(user).getId();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_USER.getMessage()));
    }
}
