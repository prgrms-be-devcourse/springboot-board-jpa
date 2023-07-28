package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.dto.UserCreationRequest;
import com.jpaboard.domain.user.dto.UserResponse;
import com.jpaboard.domain.user.dto.UserUpdateRequest;

public interface UserService {
    Long save(UserCreationRequest request);

    UserResponse findById(Long id);

    void updateById(Long id, UserUpdateRequest request);

    void deleteById(Long id);
}
