package com.example.board.domain.service;


import com.example.board.domain.entity.User;
import com.example.board.domain.entity.repository.UserRepository;
import com.example.board.dto.user.UserResponseDto;
import com.example.board.dto.user.UserSaveRequestDto;
import com.example.board.dto.user.UserUpdateRequestDto;
import com.example.board.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserSaveRequestDto requestDto) {
        User saveUser = requestDto.toEntity();
        return userRepository.save(saveUser).getId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUser(Long userId) {
        User findUser = getUser(userId);
        return findUser.from();
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        //dirty checking
        User findUser = getUser(userId);
        findUser.update(requestDto);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User findUser = getUser(userId);
        userRepository.delete(findUser);
    }

    //다른 서비스 계층에서 사용하기 위해 public , @Transactional 리팩토링
    @Transactional
    public User getUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 유저를 찾을 수 없습니다"));
        return findUser;
    }
}
