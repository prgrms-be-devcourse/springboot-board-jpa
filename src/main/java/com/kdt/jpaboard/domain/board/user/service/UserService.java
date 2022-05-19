package com.kdt.jpaboard.domain.board.user.service;

import com.kdt.jpaboard.domain.board.user.User;
import com.kdt.jpaboard.domain.board.user.UserRepository;
import com.kdt.jpaboard.domain.board.user.converter.UserConverter;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public Long save(CreateUserDto userDto) {
        User user = userConverter.convertNewUser(userDto);
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userConverter::convertUserDto);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long userId) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("찾는 user가 없습니다."));
        return userConverter.convertUserDto(user);
    }


    @Transactional
    public Long update(Long userId, CreateUserDto userDto) throws NotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("업데이트할 사용자를 찾지 못했습니다."));

        user.changeUserInfo(userDto.getName(), userDto.getAge(), userDto.getHobby());
        User update = userRepository.save(user);
        return update.getId();
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}
