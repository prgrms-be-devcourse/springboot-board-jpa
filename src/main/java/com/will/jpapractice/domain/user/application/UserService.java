package com.will.jpapractice.domain.user.application;

import com.will.jpapractice.domain.user.dto.UserRequest;
import com.will.jpapractice.domain.user.dto.UserResponse;
import com.will.jpapractice.global.converter.Converter;
import com.will.jpapractice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final Converter converter;

    @Transactional
    public Long save(UserRequest userRequest) {
        return userRepository.save(converter.toUser(userRequest)).getId();
    }

    public Page<UserResponse> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(converter::toUserResponse);
    }

}
