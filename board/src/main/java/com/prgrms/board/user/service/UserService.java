package com.prgrms.board.user.service;

import com.prgrms.board.config.NotFoundException;
import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.dto.UserRequest;
import com.prgrms.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.board.user.converter.UserConverter.convertUser;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User insert(UserRequest userRequest) {
        User user = convertUser(userRequest);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findOne(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Slice<User> findAll(Pageable pageable) {
        return userRepository.findByPageable(pageable);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}
