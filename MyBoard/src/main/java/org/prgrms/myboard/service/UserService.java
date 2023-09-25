package org.prgrms.myboard.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.prgrms.myboard.dto.UserCreateRequestDto;
import org.prgrms.myboard.dto.UserResponseDto;
import org.prgrms.myboard.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toUser();
        userRepository.save(user);
        return UserResponseDto.from(user);
    }

}
