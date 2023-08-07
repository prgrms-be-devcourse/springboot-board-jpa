package com.prgrms.boardjpa.User.application;

import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.User.domain.UserRepository;
import com.prgrms.boardjpa.User.dto.UserRequest;
import com.prgrms.boardjpa.User.dto.UserResponse;
import com.prgrms.boardjpa.global.ErrorCode;
import com.prgrms.boardjpa.global.exception.BusinessServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse join(UserRequest request) {
        validateDuplicateName(request.getName());

        User user = request.toEntity();
        User savedUser = userRepository.save(user);

        return UserResponse.create(savedUser);
    }


    private void validateDuplicateName(String name) {
        if (userRepository.existsByName(name)) {
            throw new BusinessServiceException(ErrorCode.DUPLICATED_NAME);
        }
    }
}