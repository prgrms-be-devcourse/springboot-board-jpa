package com.kdt.board.user.application;

import com.kdt.board.user.application.dto.request.RegistrationUserRequestDto;
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
    public void register(RegistrationUserRequestDto registrationUserRequestDto) {
        if (registrationUserRequestDto == null) {
            throw new IllegalArgumentException();
        }

        userRepository.save(registrationUserRequestDto.toEntity());
    }
}
