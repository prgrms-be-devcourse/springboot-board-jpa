package com.kdt.board.user.application;

import com.kdt.board.user.application.dto.request.UserRegistrationRequestDto;
import com.kdt.board.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRegistrationRequestDto == null) {
            throw new IllegalArgumentException();
        }

        userRepository.save(userRegistrationRequestDto.toEntity());
    }
}
