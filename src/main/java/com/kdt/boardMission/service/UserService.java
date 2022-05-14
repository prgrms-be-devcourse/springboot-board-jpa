package com.kdt.boardMission.service;

import com.kdt.boardMission.domain.User;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.boardMission.dto.UserDto.convertUser;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long saveUser(UserDto userDto) {
        User user = convertUser(userDto);
        User save = userRepository.save(user);
        return save.getId();
    }

}
