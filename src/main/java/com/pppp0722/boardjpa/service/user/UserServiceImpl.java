package com.pppp0722.boardjpa.service.user;

import com.pppp0722.boardjpa.domain.user.User;
import com.pppp0722.boardjpa.domain.user.UserRepository;
import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User user = userRepository.save(userRequestDto.to());
        return UserResponseDto.from(user);
    }

    @Override
    @Transactional
    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
            .map(UserResponseDto::from)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(UserResponseDto::from);
    }

    @Override
    @Transactional
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        user.setAge(userRequestDto.getAge());
        user.setHobby(userRequestDto.getHobby());
        user.setName(userRequestDto.getName());

        User updatedUser = userRepository.save(user);
        return UserResponseDto.from(updatedUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
