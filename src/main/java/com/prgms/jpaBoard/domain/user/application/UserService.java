package com.prgms.jpaBoard.domain.user.application;

import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.domain.user.UserRepository;
import com.prgms.jpaBoard.domain.user.application.dto.UserResponse;
import com.prgms.jpaBoard.domain.user.presentation.dto.UserSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(UserSaveRequest userSaveRequest) {
        User user = UserMapper.from(userSaveRequest);
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public UserResponse findOne(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        return new UserResponse(findUser);
    }
}
