package com.kdt.boardMission.service;

import com.kdt.boardMission.domain.User;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kdt.boardMission.dto.UserDto.convertUser;
import static com.kdt.boardMission.dto.UserDto.convertUserDto;

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

    public void deleteUser(UserDto userDto) {
        User user = convertUser(userDto);
        userRepository.delete(user);
    }

    public UserDto findUserById(UserDto userDto) throws NotFoundException {
        Optional<User> byId = userRepository.findById(userDto.getId());
        if (byId.isEmpty()) {
            throw new NotFoundException("해당 아이디를 가진 유저가 없습니다.");
        }
        User user = byId.get();
        return convertUserDto(user);
    }

    public Page<UserDto> findUserByName(UserDto userDto, Pageable pageable) {
        String name = userDto.getName();
        return userRepository.findByName(name, pageable).map(UserDto::convertUserDto);
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::convertUserDto);
    }
}
