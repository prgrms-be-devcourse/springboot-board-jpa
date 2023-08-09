package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;

public interface UserService {

    Long createUser(UserCreationRequest request);

    UserResponse findUserById(Long id);

    void updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);
}
